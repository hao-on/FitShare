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
import android.content.Loader
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.Toast
import com.example.fitshare.Feeds.CommentFragment
import com.example.fitshare.Feeds.FeedsFragment
import com.example.fitshare.Feeds.UploadPostDialog
import com.example.fitshare.Profile.ProfileFragment
import com.example.fitshare.Profile.ProfileMenu
import com.example.fitshare.Recipe.BottomDialog
import com.example.fitshare.Recipe.RecipeFragment
import com.example.fitshare.WorkOutPlan.WorkOutPlanActivity
import com.google.android.material.bottomappbar.BottomAppBar


class MainActivity : AppCompatActivity() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var fab: FloatingActionButton
    private lateinit var workoutFab: FloatingActionButton
    private lateinit var nutritionFab: FloatingActionButton
    private lateinit var exerciseFab: FloatingActionButton
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var bottomAppbar: BottomAppBar
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
        openFragment(FeedsFragment())
        bottomAppbar = findViewById(R.id.bottomAppBar)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.newsFeed-> {
                    openFragment(FeedsFragment())
                    return@OnItemSelectedListener true
                }
                R.id.fitness-> {
                    openFragment(FitnessFragment())
                    return@OnItemSelectedListener true
                }
                R.id.recipe -> {
                    openFragment(RecipeFragment())
                    return@OnItemSelectedListener true
                }
                R.id.profile -> {
                    openFragment(ProfileFragment())
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
            if (currFragment is ProfileFragment) {
                val openProfileMenu : ProfileMenu = ProfileMenu.newInstance()
                openProfileMenu.show(supportFragmentManager, null)
            }
            if (currFragment is FeedsFragment) {
                val addBottomDialog : UploadPostDialog = UploadPostDialog.newInstance()
                addBottomDialog.show(supportFragmentManager, null)
            }
        })

        workoutFab = findViewById(R.id.workoutBtn)
        workoutFab.setOnClickListener{
            //Toast.makeText(this, "WorkOut Button Clicked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, WorkOutPlanActivity::class.java))
        }

        nutritionFab = findViewById(R.id.nutritionBtn)
        nutritionFab.setOnClickListener{
            Toast.makeText(this, "Nutrition Button Clicked", Toast.LENGTH_SHORT).show()
        }

        exerciseFab = findViewById(R.id.exerciseBtn)
        exerciseFab.setOnClickListener{
            Toast.makeText(this, "Exercise Button Clicked", Toast.LENGTH_SHORT).show()
            val currFragment: Fragment? =
                supportFragmentManager.findFragmentById(R.id.frameLayout)

            if (currFragment is FitnessFragment) {
                val addBottomDialogExercise: BottomDialogExercise =
                    BottomDialogExercise.newInstance()
                addBottomDialogExercise.show(supportFragmentManager, null)
            }
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
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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
            exerciseFab.visibility = View.INVISIBLE
        }
        else{
            workoutFab.visibility = View.VISIBLE
            nutritionFab.visibility = View.VISIBLE
            exerciseFab.visibility = View.VISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked){
            workoutFab.startAnimation(fromBottomAnimation)
            nutritionFab.startAnimation(fromBottomAnimation)
            exerciseFab.startAnimation(fromBottomAnimation)
            fab.startAnimation(rotateOpenAnimation)
        }else{
            workoutFab.startAnimation(toBottomAnimation)
            nutritionFab.startAnimation(toBottomAnimation)
            exerciseFab.startAnimation(toBottomAnimation)
            fab.startAnimation(rotateCloseAnimation)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            workoutFab.isClickable = true
            nutritionFab.isClickable = true
            exerciseFab.isClickable = true
        }
        else{
            workoutFab.isClickable = false
            nutritionFab.isClickable = false
            exerciseFab.isClickable = false
        }
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        bottomAppBar.visibility = visibility
        fab.visibility = visibility
    }
}