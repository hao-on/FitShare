package com.example.fitshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.material.navigation.NavigationBarView
import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.FragmentTransaction
import com.example.fitshare.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var fab: FloatingActionButton

    override fun onStart() {
        super.onStart()
        user = fitApp.currentUser()
        if (user == null) {
            // if no user is currently logged in, start the login activity so the user can authenticate
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(FeedsFragment.newInstance("",""))

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.newsFeed-> {
                    openFragment(FeedsFragment.newInstance("",""))
                    return@OnItemSelectedListener true
                }
                R.id.fitness-> {
                    openFragment(FitnessFragment.newInstance("",""))
                    return@OnItemSelectedListener true
                }
                R.id.recipe -> {
                    openFragment(RecipeFragment.newInstance("",""))
                    return@OnItemSelectedListener true
                }
                R.id.profile -> {
                    openFragment(ProfileFragment.newInstance("",""))
                    return@OnItemSelectedListener true
                }
            }
            false
        })
        /*
        val posts: ArrayList<Post> = ArrayList()
        for ( i in 0..1){
            posts.add(Post( "Dev_Team_"+i, "Welcome to FitShare!!!", "https://static01.nyt.com/images/2019/02/28/opinion/28yun/28yun-superJumbo.jpg"))
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PostsAdapter(posts, this)
        */
    }

    private fun openFragment(fragment: Fragment) {
        Log.d(TAG, "openFragment: ")
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        //this is a helper class that replaces the container with the fragment. You can replace or add fragments.
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null) //if you add fragments it will be added to the backStack. If you replace the fragment it will add only the last fragment
        transaction.commit() // commit() performs the action
    }

}