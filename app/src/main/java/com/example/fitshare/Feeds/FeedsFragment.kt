package com.example.fitshare.Feeds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.Profile.Profile
import com.example.fitshare.R
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.Recipe.RecipeAdapter
import com.example.fitshare.fitApp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_feeds.*
import kotlinx.android.synthetic.main.fragment_recipe.*

class FeedsFragment : Fragment() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var feedsRealm: Realm
    private lateinit var profileRealm: Realm
    private lateinit var userRealm: Realm
    private lateinit var partition: String
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_feeds, container, false)
        user = fitApp.currentUser()
        partition = "Feeds"
        val config = SyncConfiguration.Builder(user!!, partition).build()

        //Profile Realm sync config
        Realm.getInstanceAsync(config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm) {
                this@FeedsFragment.feedsRealm = realm
                rvFeeds.layoutManager =
                    LinearLayoutManager(requireActivity().applicationContext)
                rvFeeds.setHasFixedSize(true)
                adapter = PostAdapter(feedsRealm.where<Post>().sort("date").findAll(), user!!, partition)
                rvFeeds.adapter = adapter
            }
        })
        return view
    }

    override fun onStop() {
        super.onStop()
        user.run {
            feedsRealm.close()
//            userRealm.close()
        }
    }

}