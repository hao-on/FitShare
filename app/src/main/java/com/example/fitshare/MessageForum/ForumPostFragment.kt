package com.example.fitshare.MessageForum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitshare.Profile.Profile
import com.example.fitshare.R
import com.example.fitshare.fitApp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration

class ForumPostFragment : Fragment() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var forumRealm: Realm
    private lateinit var profileRealm: Realm
    private lateinit var partition: String
    private lateinit var addPost: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState : Bundle?
    ): View?{
        val view: View = inflater.inflate(R.layout.forum_fragment, container, false)
        user = fitApp.currentUser()

        partition = "Forum"
        val config = SyncConfiguration.Builder(user!!, partition).build()

        Realm.getInstanceAsync(config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm){
                this@ForumPostFragment.forumRealm = realm

            }
        })

        partition = "Profile"
        val config_prof = SyncConfiguration.Builder(user!!, partition).build()

        Realm.getInstanceAsync(config_prof, object: Realm.Callback(){
            override fun onSuccess(realm: Realm){
                this@ForumPostFragment.profileRealm = realm

                val profile = profileRealm.where(Profile::class.java).
                equalTo("userid", user?.id.toString()).findFirst()
            }
        })

        addPost = view.findViewById(R.id.addPostBtn)
        addPost.setOnClickListener{

        }

        return view
    }

    override fun onDestroy(){
        super.onDestroy()
        forumRealm.close()
    }
}