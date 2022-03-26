package com.example.fitshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fitshare.Recipe.Recipe
import kotlinx.android.synthetic.main.activity_add_recipe.*
import io.realm.Realm
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import org.bson.types.ObjectId

class AddRecipe : AppCompatActivity() {
    private lateinit var recipeRealm: Realm
    private var user: User? = null

    override fun onStart() {
        super.onStart()
        user = fitApp.currentUser()
        if (user == null) {
            // if no user is currently logged in, start the login activity so the user can authenticate
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            /*
            val config = SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .allowWritesOnUiThread(true)
                .allowQueriesOnUiThread(true)
                .build()

            // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
            Realm.getInstanceAsync(config, object : Realm.Callback() {
                override fun onSuccess(realm: Realm) {
                    // since this realm should live exactly as long as this activity, assign the realm to a member variable
                    this@AddRecipe.recipeRealm = realm
                }
            })
            */
            val config : SyncConfiguration =
                SyncConfiguration.Builder(fitApp.currentUser(), "recipe")
                    .allowQueriesOnUiThread(true)
                    .allowWritesOnUiThread(true)
                    .build()
            val realm = Realm.getInstance(config)

// create an reference to a frog
            var recipe : Recipe? = null
// insert a new frog into the database and store it in our reference
            realm.executeTransaction { transactionRealm: Realm ->
                recipe = transactionRealm.createObject(Recipe::class.java, ObjectId())
                recipe?.recipeName = "Taco"
                recipe?.description = "Yummy Taco"
                recipe?.ingredients = "Fish and Tortilla"
                recipe?.steps = "Just Cook"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)
        recipe_button.setOnClickListener{ createRecipe() }
    }


    private fun createRecipe(){
        val recipe = Recipe("Taco" ,
            "Yummy Taco", "Fish, toritlla", "Wrap and Cook")

        recipeRealm.executeTransactionAsync { realm -> realm.insert(recipe)}

    }
}