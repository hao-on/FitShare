package com.example.fitshare.Messaging

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.R
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.Recipe.RecipeAdapter
import com.example.fitshare.User.User
import com.example.fitshare.fitApp
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration

class MessageActivity : AppCompatActivity() {
    private var user : io.realm.mongodb.User? = null
    private lateinit var partition: String
    private lateinit var messageRealm: Realm

    override fun onStart() {
        super.onStart()
        user = fitApp.currentUser()
        partition = "Message"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()
        // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@MessageActivity.messageRealm = realm
                setUpRecyclerView(realm, user, partition)
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }

    override fun onStop() {
        super.onStop()
        user.run {
            messageRealm.close()
        }
    }

    private fun setUpRecyclerView(realm: Realm, user: io.realm.mongodb.User?, partition: String) {
//        adapter = RecipeAdapter(realm.where<Recipe>().contains("_partition", partition).
//        sort("recipeName").findAll(), user!!, partition)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter
//        recyclerView.setHasFixedSize(true)
//        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}