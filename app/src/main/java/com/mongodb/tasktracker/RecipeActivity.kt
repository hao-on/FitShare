package com.mongodb.tasktracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mongodb.tasktracker.model.ProjectAdapter
import com.mongodb.tasktracker.model.Recipe
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration

class RecipeActivity: AppCompatActivity()  {
    private var user: io.realm.mongodb.User? = null
    private var userRealm: Realm? = null
    private lateinit var recipeRealm: Realm
   // private lateinit var recyclerView: RecyclerView
   // private lateinit var adapter: ProjectAdapter

    override fun onStart() {
        super.onStart()
        user = taskApp.currentUser()
        if (user == null) {
            // if no user is currently logged in, start the login activity so the user can authenticate
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            // configure realm to use the current user and the partition corresponding to the user's project
            val config = SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .build()

            // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
            Realm.getInstanceAsync(config, object: Realm.Callback() {
                override fun onSuccess(realm: Realm) {
                    // since this realm should live exactly as long as this activity, assign the realm to a member variable
                    this@RecipeActivity.userRealm = realm
                 //   this@ProjectActivity.userRealm = realm
                  //  setUpRecyclerView(getProjects(realm))
                }
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = taskApp.currentUser()
       // setContentView(R.layout.activity_project)

        setContentView(R.layout.recipe_test)

        val recipe = user?.let {
            Recipe("test name","testdes","test ing",
                "test step step", it.id)
        }
       // val recipe = Recipe("test","test des","test ingr", "test step")

        userRealm?.executeTransactionAsync { realm ->
            realm.insert(recipe)  }


      //  recyclerView = findViewById(R.id.project_list)
//        var recipe = Recipe("test recipe name")
//        projectRealm.executeTransactionAsync { realm ->
//            realm.insert(recipe)
//        }
    }

    override fun onStop() {
        super.onStop()
        user.run {
            userRealm?.close()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        userRealm?.close()
       // recyclerView.adapter = null
    }
}

