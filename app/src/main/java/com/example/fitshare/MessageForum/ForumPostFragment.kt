package com.example.fitshare.MessageForum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.Profile.Profile
import com.example.fitshare.Profile.ProfileEditButton
import com.example.fitshare.R
import com.example.fitshare.fitApp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Case
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration

class ForumPostFragment : Fragment() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var forumRealm: Realm
    private lateinit var profileRealm: Realm
    private lateinit var partition: String
    private lateinit var profPartition: String
    private lateinit var addPost: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var forumAdapter: ForumPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState : Bundle?
    ): View?{
        val view: View = inflater.inflate(R.layout.forum_fragment, container, false)
        user = fitApp.currentUser()
        partition = "Forum"
        recyclerView = view.findViewById(R.id.rvPost)

        val config = SyncConfiguration.Builder(user!!, partition).build()

        Realm.getInstanceAsync(config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm){
                this@ForumPostFragment.forumRealm = realm
                setUpRecyclerView(recyclerView, forumRealm, user, partition)
            }
        })

        profPartition = "Profile"
        val config_prof = SyncConfiguration.Builder(user!!, profPartition).build()

        Realm.getInstanceAsync(config_prof, object: Realm.Callback(){
            override fun onSuccess(realm: Realm){
                this@ForumPostFragment.profileRealm = realm

                val profile = profileRealm.where(Profile::class.java).
                equalTo("userid", user?.id.toString()).findFirst()
            }
        })

        addPost = view.findViewById(R.id.addPostBtn)
        addPost.setOnClickListener{
            val addForumPost : ForumPostBtnDialog = ForumPostBtnDialog.newInstance()
            addForumPost.show(parentFragmentManager, null)
        }

        return view
    }

//    private fun recyclerSearch(realm: Realm, user: User?, partition: String, text: String){
//        forumAdapter = ForumPostAdapter(realm.where<ForumPost>().contains("title", text, Case.INSENSITIVE)
//            .findAll(), user!!, partition)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext().applicationContext)
//        recyclerView.adapter = forumAdapter
//        recyclerView.setHasFixedSize(true)
//        recyclerView.addItemDecoration(DividerItemDecoration(requireContext().applicationContext, DividerItemDecoration.VERTICAL))
//    }

    private fun setUpRecyclerView(recyclerView: RecyclerView, realm: Realm, user: User?, partition: String) {
        forumAdapter = ForumPostAdapter(realm.where<ForumPost>().contains("_partition", partition).
        sort("title").findAll(), user!!, partition)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        recyclerView.adapter = forumAdapter
        recyclerView.setHasFixedSize(true)
    }

    override fun onDestroy(){
        super.onDestroy()
        forumRealm.close()
    }
}