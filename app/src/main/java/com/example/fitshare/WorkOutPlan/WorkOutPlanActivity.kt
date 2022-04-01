package com.example.fitshare.WorkOutPlan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.fitshare.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class WorkOutPlanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_out_plan)

        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val viewPage: ViewPager2 = findViewById(R.id.pager)
        val adapter = PlanCollectionAdapter(supportFragmentManager, lifecycle)
        viewPage.adapter = adapter

        var plans = arrayOf("Plan Core", "Plan Pull", "Plan Push", "PLan Leg")
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = "${plans[position]}"
        }.attach()

    }
}

/*
override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate (savedInstanceState)
        setContentView(R.layout.workoutplan_main)

        user = fitApp.currentUser()
        partition="plan_core"

        val config = SyncConfiguration.Builder(user!!, partition)
            .build()
        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@workout.exerciseRealm = realm
            }
        })

        val user_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .build()
        Realm.getInstanceAsync(user_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@workout.userRealm = realm
            }
        })

         //set on-click listener
        planCore.setOnClickListener {
            /*
            val exercise1 = Exercise(
                "Crunches",
                3,
                15,
                0.0)
            val exercise2 = Exercise(
                "Russian Twist",
                3,
                15,
                0.0)
            planRealm.executeTransactionAsync { realm ->
                realm.insert(exercise1)
                realm.insert(exercise2)
            }
            */
            rvCore.layoutManager = LinearLayoutManager(this)
            rvCore.setHasFixedSize(true)
            adapter = ExerciseAdapter(exerciseRealm.where<Exercise>()
                .sort("exerciseName").findAll(), user!!, partition)
            rvCore.adapter = adapter
        }
    }
 */