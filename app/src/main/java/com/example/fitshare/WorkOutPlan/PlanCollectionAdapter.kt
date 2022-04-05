package com.example.fitshare.WorkOutPlan

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PlanCollectionAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = PlanFragment()
        var plans = arrayOf("plan_core", "plan_pull", "plan_push", "plan_leg")
        val bundle = Bundle()
        bundle.putString("planName", plans[position])
        fragment.arguments = bundle
        return fragment
    }
}