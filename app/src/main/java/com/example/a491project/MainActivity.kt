package com.example.a491project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a491project.model.Post
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        /*
        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        */
        setContentView(R.layout.activity_main)

        val posts: ArrayList<Post> = ArrayList()
        for ( i in 0..1){
            posts.add(Post( "Dev_Team_"+i, "Welcome to FitShare!!!", "https://static01.nyt.com/images/2019/02/28/opinion/28yun/28yun-superJumbo.jpg"))
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PostsAdapter(posts, this)

    }
}