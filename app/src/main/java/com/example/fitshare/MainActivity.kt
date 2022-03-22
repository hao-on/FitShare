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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Toast





class MainActivity : AppCompatActivity() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var fab: FloatingActionButton
    private lateinit var workoutFab: FloatingActionButton
    private lateinit var nutritionFab: FloatingActionButton
    private val rotateOpenAnimation: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_animation)}
    private val rotateCloseAnimation: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_animation)}
    private val fromBottomAnimation: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_animation)}
    private val toBottomAnimation: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_animation)}
    private var clicked: Boolean = false

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

        fab = findViewById(R.id.uploadBtn)
        fab.setOnClickListener(View.OnClickListener {
            val currFragment: Fragment? =
                supportFragmentManager.findFragmentById(R.id.frameLayout)

            if (currFragment is RecipeFragment) {
                val addBottomDialog : BottomDialog = BottomDialog.newInstance()
                addBottomDialog.show(supportFragmentManager, null)
            }
            if (currFragment is FitnessFragment) {
                onAddButtonClicked()
            }
        })

        workoutFab = findViewById(R.id.workoutBtn)
        workoutFab.setOnClickListener{
            //Toast.makeText(this, "WorkOut Button Clicked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, WorkoutPlanActivity::class.java))
        }

        nutritionFab = findViewById(R.id.nutritionBtn)
        nutritionFab.setOnClickListener{
            Toast.makeText(this, "Nutrition Button Clicked", Toast.LENGTH_SHORT).show()
        }

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

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            workoutFab.visibility = View.INVISIBLE
            nutritionFab.visibility = View.INVISIBLE
        }
        else{
            workoutFab.visibility = View.VISIBLE
            nutritionFab.visibility = View.VISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked){
            workoutFab.startAnimation(fromBottomAnimation)
            nutritionFab.startAnimation(fromBottomAnimation)
            fab.startAnimation(rotateOpenAnimation)
        }else{
            workoutFab.startAnimation(toBottomAnimation)
            nutritionFab.startAnimation(toBottomAnimation)
            fab.startAnimation(rotateCloseAnimation)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            workoutFab.isClickable = true
            nutritionFab.isClickable = true
        }
        else{
            workoutFab.isClickable = false
            nutritionFab.isClickable = false
        }
    }

}