package com.example.fitshare.Messaging

import android.util.Log
import com.example.fitshare.FitModule
import com.example.fitshare.Profile.Profile
import com.example.fitshare.User.User
import com.example.fitshare.fitApp
import io.realm.OrderedRealmCollectionChangeListener
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.mongodb.sync.SyncConfiguration

object ChatManager {



    var profileEntries: RealmResults<Profile>
    var msgEntries: RealmResults<Message>
    var user: io.realm.mongodb.User? = null
    lateinit var profile: Profile
    private lateinit var partition: String
    private lateinit var profileRealm: Realm
    private lateinit var messageRealm: Realm
    private lateinit var username: String
    private var usersInitialized: Boolean = false
    private var profListeners: MutableList<ProfileListener> = mutableListOf<ProfileListener>()
    private var chatListeners: MutableList<ChatListener> = mutableListOf<ChatListener>()

    class ProfileListener(var listener: (RealmResults<Profile>) -> Unit) {}
    class ChatListener(var listener: (RealmResults<Message>) -> Unit) {}


    init {
        profileEntries = profileRealm.where(Profile::class.java).findAllAsync()!!
        msgEntries = messageRealm.where(Message::class.java).sort("time", Sort.ASCENDING)!!.findAllAsync()

        user = fitApp.currentUser()

        //Initialize Profile Realm
        val prof_config = SyncConfiguration.Builder(user!!, "Profile")
            .build()
        // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
        Realm.getInstanceAsync(prof_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@ChatManager.profileRealm = realm
                val oldProf = profileRealm.where(Profile::class.java).
                equalTo("userid", user?.id.toString()).findFirst()
                username = oldProf?.username.toString()
            }
        })

        //Initialize Message Realm
        partition = "Message"
        val message_config = SyncConfiguration.Builder(user!!, partition)
            .build()
        // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
        Realm.getInstanceAsync(message_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@ChatManager.messageRealm = realm
            }
        })

        profileEntries.addChangeListener(OrderedRealmCollectionChangeListener<RealmResults<Profile>> { collection, changeSet ->
            if (!usersInitialized) {

                if (!ChatManager::profile.isInitialized) {
                    var _profile = username!!
                    collection.forEach() {
                        if (it.username == _profile)
                        {
                            profile = it
                            usersInitialized = true
                        }
                    }
                }

                profListeners.forEach() {
                    it.listener(profileEntries)
                }

                msgEntries.addChangeListener(OrderedRealmCollectionChangeListener<RealmResults<Message>> { collection, changeSet ->
                    Log.i("User Observable", "Initialized")
                    chatListeners.forEach {
                        it.listener(msgEntries)
                    }
                })
            }
        })
    }

    fun AddChatListener(listener: (RealmResults<Message>) -> Unit) {
        chatListeners.add(ChatListener(listener))
    }

    fun AddProfileListener(listener: (RealmResults<Profile>) -> Unit) {
        profListeners.add(ProfileListener(listener))
    }
}