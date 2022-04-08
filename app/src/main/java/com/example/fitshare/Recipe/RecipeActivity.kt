package com.example.fitshare.Recipe

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.LoginActivity
import com.example.fitshare.R
import com.example.fitshare.fitApp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Case
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration


class RecipeActivity : AppCompatActivity() {
    private lateinit var projectRealm: Realm
    private var user: User? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var partition: String


    override fun onStart() {
        super.onStart()
        user = fitApp.currentUser()
        partition = "recipe"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()
        // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@RecipeActivity.projectRealm = realm
                setUpRecyclerView(realm, user, partition)
            }
        })

    }

    override fun onStop() {
        super.onStop()
        user.run {
            projectRealm.close()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_page)
        recyclerView = findViewById(R.id.task_list)
        fab = findViewById(R.id.uploadBtn)

        // create a dialog to enter a task name when the floating action button is clicked
        fab.setOnClickListener {

            val dialogBuilder = AlertDialog.Builder(this)
            val scrollView = ScrollView(this)
            val layout = LinearLayout(this)
            layout.setOrientation(LinearLayout.VERTICAL)
            scrollView.addView(layout)

            val nameInput = EditText(this)
            nameInput.setHint("Name")
            layout.addView(nameInput)

            val descInput = EditText(this)
            descInput.setHint("Description")
            layout.addView(descInput)

            val ingrInput = EditText(this)
            ingrInput.setHint("Ingredients")
            layout.addView(ingrInput)

            val stepInput = EditText(this)
            stepInput.setHint("Steps")
            layout.addView(stepInput)

            dialogBuilder.setCancelable(true).setPositiveButton("Submit") {dialog, _ -> run{
                dialog.dismiss()

                val recipe = Recipe(nameInput.text.toString(),
                    descInput.text.toString(),
                    ingrInput.text.toString(),
                    stepInput.text.toString())

                projectRealm.executeTransactionAsync { realm -> realm.insert(recipe)}

            }
            }.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel()}

            val dialog = dialogBuilder.create()
            dialog.setView(scrollView)
            dialog.setTitle("Adding Recipe..." + "\n" + "Enter Recipe Information")
            dialog.show()
            dialog.getWindow()?.setLayout(850, 965)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView.adapter = null
        // if a user hasn't logged out when the activity exits, still need to explicitly close the realm
        projectRealm.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setMaxWidth(Int.MAX_VALUE)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(text: String): Boolean {
                recyclerSearch(projectRealm, user, partition, text)
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                recyclerSearch(projectRealm, user, partition, text)
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }


    private fun recyclerSearch(realm: Realm, user: User?, partition: String, text: String){
        adapter = RecipeAdapter(realm.where<Recipe>().contains("recipeName", text, Case.INSENSITIVE)
            .findAll(), user!!, partition)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    }

    private fun setUpRecyclerView(realm: Realm, user: User?, partition: String) {
        // a recyclerview requires an adapter, which feeds it items to display.
        // Realm provides RealmRecyclerViewAdapter, which you can extend to customize for your application
        // pass the adapter a collection of Recipes from the realm
        // sort this collection so that the displayed order of Recipes remains stable across updates
        adapter = RecipeAdapter(realm.where<Recipe>().sort("recipeName").findAll(), user!!, partition)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

}