package com.mongodb.tasktracker

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongodb.tasktracker.model.Recipe
import com.mongodb.tasktracker.model.RecipeAdapter
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.User

class FitnessMainActivity {


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
}