package com.example.fitshare.WorkOutPlan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.fitshare.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_work_out_plan.*

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

        showBasket.setOnClickListener{
            goToCart()
        }

    }

    private fun goToCart() {
        val intent = ShoppingCartActivity.newIntent(this)
        startActivity(intent)
    }
}