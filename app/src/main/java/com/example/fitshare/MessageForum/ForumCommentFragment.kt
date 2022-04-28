package com.example.fitshare.MessageForum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.R
import com.example.fitshare.fitApp
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.forum_comment_fragment.*
import kotlinx.android.synthetic.main.forum_fragment.*
import kotlinx.android.synthetic.main.forum_fragment.rvPost

class ForumCommentFragment : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private var user: io.realm.mongodb.User ?= null
    private lateinit var partition: String
    private lateinit var commentAdapter: ForumCommentAdapter
    private lateinit var commentRealm: Realm
    private lateinit var sendButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState : Bundle?
    ): View?{
        val view: View = inflater.inflate(R.layout.forum_comment_fragment, container, false)
        user = fitApp.currentUser()
        partition = "Forum"
        recyclerView = view.findViewById(R.id.rvComment)

        val config = SyncConfiguration.Builder(user!!, partition).build()

        Realm.getInstanceAsync(config, object: Realm.Callback(){
            override fun onSuccess(realm: Realm){
                this@ForumCommentFragment.commentRealm = realm

                commentAdapter = ForumCommentAdapter(commentRealm.where<ForumComment>().findAll(), user!!, partition)
                rvComment.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
                rvComment.adapter = commentAdapter

                rvPost.setHasFixedSize(true)
            }
        })

        sendButton = view.findViewById(R.id.msgBtn)
        sendButton.setOnClickListener{

        }

        return view
    }
}