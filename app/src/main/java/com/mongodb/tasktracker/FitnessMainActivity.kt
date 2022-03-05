package com.mongodb.tasktracker

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mongodb.tasktracker.model.Project
import com.mongodb.tasktracker.model.Exercise
import com.mongodb.tasktracker.model.ExerciseAdapter
import com.mongodb.tasktracker.model.Food
import com.mongodb.tasktracker.model.FoodAdapter
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration

class FitnessMainActivity : AppCompatActivity()  {
    private lateinit var exerciseRealm: Realm
    private var user: User? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var partition: String

    override fun onStart() {
        super.onStart()
        user = taskApp.currentUser()
        if (user == null) {
            // if no user is currently logged in, start the login activity so the user can authenticate
            startActivity(Intent(this, LoginActivity::class.java))
        }
        else {
            // get the partition value and name of the project we are currently viewing
            partition = intent.extras?.getString(PARTITION_EXTRA_KEY)!!
            val projectName = intent.extras?.getString(PROJECT_NAME_EXTRA_KEY)

            // display the name of the project in the action bar via the title member variable of the Activity
            title = projectName

            val config = SyncConfiguration.Builder(user!!, partition)
                .build()

            // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
            Realm.getInstanceAsync(config, object: Realm.Callback() {
                override fun onSuccess(realm: Realm) {
                    // since this realm should live exactly as long as this activity, assign the realm to a member variable
                    this@FitnessMainActivity.exerciseRealm = realm
                    setUpRecyclerView(realm, user, partition)
                }
            })
        }
    }

    override fun onStop() {
        super.onStop()
        user.run {
            exerciseRealm.close()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        recyclerView = findViewById(R.id.task_list)
        fab = findViewById(R.id.floating_action_button)

        // create a dialog to enter a task name when the floating action button is clicked
        fab.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(this)

            val layout = LinearLayout(this)
            layout.setOrientation(LinearLayout.VERTICAL)
            dialogBuilder.setMessage("Enter Exercise Information!")

            val nameInput = EditText(this)
            nameInput.setHint("Name")
            layout.addView(nameInput)

            val setsInput = EditText(this)
            setsInput.setHint("Sets")
            layout.addView(setsInput)

            val repsInput = EditText(this)
            repsInput.setHint("Reps")
            layout.addView(repsInput)

            val weightInput = EditText(this)
            weightInput.setHint("Weight")
            layout.addView(weightInput)

            dialogBuilder.setCancelable(true).setPositiveButton("Submit") {dialog, _ -> run{
                dialog.dismiss()

                val exercise = Exercise(nameInput.text.toString(), setsInput.text, repsInput.text.toString(), weightInput.text.toString(), user?.id.toString())

                exerciseRealm.executeTransactionAsync { realm -> realm.insert(exercise)}

            }
            }.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel()}

            val dialog = dialogBuilder.create()
            dialog.setView(layout)
            dialog.setTitle("Adding Exercise...")
            dialog.show()
            dialog.getWindow()?.setLayout(850, 1000)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        recyclerView.adapter = null
        // if a user hasn't logged out when the activity exits, still need to explicitly close the realm
        projectRealm.close()
    }



    private fun setUpRecyclerView(realm: Realm, user: User?, partition: String) {
        // a recyclerview requires an adapter, which feeds it items to display.
        // Realm provides RealmRecyclerViewAdapter, which you can extend to customize for your application
        // pass the adapter a collection of Recipes from the realm
        // sort this collection so that the displayed order of Recipes remains stable across updates
        adapter = RecipeAdapter(realm.where<Exercise>().sort("id").findAll(), user!!, partition)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun getExercises(realm: Realm): RealmList<Project> {
        // query for a user object in our user realm, which should only contain our user object
        val syncedUsers : RealmResults<com.mongodb.tasktracker.model.User> = realm.where<com.mongodb.tasktracker.model.User>().sort("id").findAll()
        val syncedUser : com.mongodb.tasktracker.model.User? = syncedUsers.getOrNull(0) // since there might be no user objects in the results, default to "null"
        // if a user object exists, create the recycler view and the corresponding adapter
        if (syncedUser != null) {
            return syncedUser.memberOf
        } else {
            // since a trigger creates our user object after initial signup, the object might not exist immediately upon first login.
            // if the user object doesn't yet exist (that is, if there are no users in the user realm), call this function again when it is created
            Log.i(TAG(), "User object not yet initialized, only showing default user project until initialization.")
            // change listener on a query for our user object lets us know when the user object has been created by the auth trigger
            val changeListener =
                OrderedRealmCollectionChangeListener<RealmResults<com.mongodb.tasktracker.model.User>> { results, changeSet ->
                    Log.i(TAG(), "User object initialized, displaying project list.")
                    setUpRecyclerView(getProjects(realm))
                }
            syncedUsers.addChangeListener(changeListener)

            // user should have a personal project no matter what, so create it if it doesn't already exist
            // RealmRecyclerAdapters only work on managed objects,
            // so create a realm to manage a fake custom user data object
            // offline, in-memory because this data does not need to be persistent or synced:
            // the object is only used to determine the partition for storing tasks
            val fakeRealm = Realm.getInstance(
                RealmConfiguration.Builder()
                    .allowWritesOnUiThread(true)
                    .inMemory().build())
            var exerciseList: RealmList<Exeercise>? = null
            var fakeCustomUserData = fakeRealm.where(com.mongodb.tasktracker.model.User::class.java).findFirst()
            if (fakeCustomUserData == null) {
                fakeRealm.executeTransaction {
                    fakeCustomUserData = it.createObject(com.mongodb.tasktracker.model.User::class.java, user?.id)
                    projectsList = fakeCustomUserData?.memberOf!!
                    projectsList?.add(Project("My Recipes", "project=${user?.id}"))
                }
            } else {
                projectsList = fakeCustomUserData?.memberOf
            }

            return projectsList!!
        }
    }

}