package com.mongodb.tasktracker

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mongodb.tasktracker.model.Recipe
import io.realm.Realm
import io.realm.mongodb.User
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import com.mongodb.tasktracker.model.TaskAdapter
import com.mongodb.tasktracker.model.Task

/*
* TaskActivity: allows a user to view a collection of Tasks, edit the status of those tasks,
* create new tasks, and delete existing tasks from the collection. All tasks are stored in a realm
* and synced across devices using the partition "project=<user id>".
*/
class TaskActivity : AppCompatActivity() {
    private lateinit var projectRealm: Realm
    private var user: User? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var partition: String
    private lateinit var recipeName: String
    private lateinit var description: String
    private lateinit var ingredients: String
    private lateinit var steps: String

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
                    this@TaskActivity.projectRealm = realm
                    setUpRecyclerView(realm, user, partition)
                }
            })
        }
    }

    override fun onStop() {
        super.onStop()
        user.run {
            projectRealm.close()
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

            dialogBuilder.setMessage("Enter Name")
            val input = EditText(this)
            input.setHint("Name")
            layout.addView(input)

            dialogBuilder.setMessage("Enter Description")
            val input2 = EditText(this)
            input2.setHint("Description")
            layout.addView(input2)

            dialogBuilder.setMessage("Enter Ingredients")
            val input3 = EditText(this)
            input2.setHint("Ingredients")
            layout.addView(input3)

            dialogBuilder.setMessage("Enter Steps")
            val input4 = EditText(this)
            input2.setHint("Steps")
            layout.addView(input4)

            dialogBuilder.setCancelable(true).setPositiveButton("Submit") {dialog, _ -> run{
                dialog.dismiss()

                val recipe = Recipe(input.text.toString(), input2.text.toString(), input3.text.toString(), input4.text.toString())
                //val task = Task("sad")
                projectRealm.executeTransactionAsync { realm -> realm.insert(recipe)}
                //projectRealm.executeTransactionAsync { realm -> realm.insert(task) }
                }
            }.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel()}

            val dialog = dialogBuilder.create()
            dialog.setView(layout)
            dialog.setTitle("Welcome")
            dialog.show()
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
        // pass the adapter a collection of Tasks from the realm
        // sort this collection so that the displayed order of Tasks remains stable across updates
        adapter = TaskAdapter(realm.where<Task>().sort("id").findAll(), user!!, partition)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
