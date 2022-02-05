package com.mongodb.tasktracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mongodb.tasktracker.model.Recipe
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.mongodb.sync.SyncConfiguration

class RecipeActivity: AppCompatActivity() {
    private lateinit var recipeRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var recyclerView: RecyclerView
    //add recipe adapter
    private lateinit var partition: String

    //Recipe declarations
    private lateinit var recipeName: EditText
    private lateinit var description: EditText
    private lateinit var ingredients: EditText
    private lateinit var steps: EditText
    private lateinit var submitButton: Button

    override fun onStart(){
        super.onStart()
//        user = taskApp.currentUser()
//        if (user == null) {
//            // if no user is currently logged in, start the login activity so the user can authenticate
//            startActivity(Intent(this, LoginActivity::class.java))
//        }
//        else {
//            // get the partition value and name of the project we are currently viewing
//            partition = intent.extras?.getString(PARTITION_EXTRA_KEY)!!
//            val projectName = intent.extras?.getString(PROJECT_NAME_EXTRA_KEY)
//
//            // display the name of the project in the action bar via the title member variable of the Activity
//            title = projectName
//
//            val config = SyncConfiguration.Builder(user!!, partition)
//                .build()
//
//            // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
//            Realm.getInstanceAsync(config, object: Realm.Callback() {
//                override fun onSuccess(realm: Realm) {
//                    // since this realm should live exactly as long as this activity, assign the realm to a member variable
//                    this@RecipeActivity.recipeRealm = realm
//                    //setUpRecyclerView(realm, user, partition)
//                }
//            })
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        recipeName = findViewById(R.id.recipe_name)
        description = findViewById(R.id.recipe_description)
        ingredients = findViewById(R.id.recipe_ingredients)
        steps = findViewById(R.id.recipe_steps)

        submitButton = findViewById(R.id.recipe_button)
        submitButton.setOnClickListener {
            val recipe = Recipe(recipeName.toString(),description.toString(),ingredients.toString(),steps.toString())

            recipeRealm.executeTransactionAsync { realm -> realm.insert(recipe) }
            Log.d("checker", "executing onCreate")

        }

    }

    override fun onStop(){
        super.onStop()
    }

    override fun onDestroy(){
        super.onDestroy()
        recipeRealm.close()
    }

    override fun onCreateOptionsMenu(menu:Menu): Boolean{
        return true
    }

    private fun getRecipes(realm: Realm): RealmList<Recipe>{
        return RealmList<Recipe>()
    }

    private fun setUpRecyclerView(recipeList: RealmList<Recipe>){
        
    }
}