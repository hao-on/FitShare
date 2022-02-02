package com.mongodb.tasktracker

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mongodb.tasktracker.model.Recipe
import io.realm.Realm
import io.realm.RealmList

class RecipeActivity: AppCompatActivity() {
    private lateinit var recipeRealm: Realm
    private var uesr: io.realm.mongodb.User? = null
    private lateinit var recyclerView: RecyclerView
    //add recipe adapter

    override fun onStart(){
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onStop(){
        super.onStop()
    }

    override fun onDestroy(){
        super.onDestroy()
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