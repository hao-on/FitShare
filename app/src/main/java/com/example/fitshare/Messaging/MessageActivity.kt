package com.example.fitshare.Messaging

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.R
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.Recipe.RecipeAdapter
import com.example.fitshare.User.User
import com.example.fitshare.fitApp
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import org.bson.types.ObjectId
import java.sql.Time
import java.time.LocalDateTime.now
import java.util.*

class MessageActivity : AppCompatActivity() {
    private var user : io.realm.mongodb.User? = null
    private lateinit var partition: String
    private lateinit var messageRealm: Realm

    private lateinit var enterMessage: EditText
    private lateinit var send: Button
    private lateinit var logout: Button
    private lateinit var messagesList: RecyclerView
    private lateinit var messagesAdapter: MessagesAdapter

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

        enterMessage = findViewById(R.id.enter_message)
        send = findViewById(R.id.send_message)
        messagesList = findViewById(R.id.messages)
        val layoutMgr = LinearLayoutManager(this)
        layoutMgr.stackFromEnd = true
        messagesList.layoutManager = layoutMgr

        messagesAdapter = MessagesAdapter("foo")
        messagesList.adapter = messagesAdapter

        send.setOnClickListener {
            sendMessage()
        }


        initializeListeners()
    }

    private fun sendMessage() {

        val message = Message(ObjectId(), ChatManager.profile.username,
            enterMessage.text.toString())
        messageRealm.executeTransaction(){ transactionRealm ->
            transactionRealm.insert(message)
        }
        enterMessage.setText("")
        scrollToBottom()
    }

    fun initializeListeners() {
        ChatManager.AddProfileListener {
            val textMe: TextView = findViewById(R.id.text_me)
            textMe.text = ChatManager.profile.username
        }

        ChatManager.AddChatListener {
            messagesAdapter.notifyDataSetChanged()
            scrollToBottom()
        }
    }

    private fun scrollToBottom() {
        messagesList.scrollToPosition(messagesAdapter.itemCount - 1)
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