package com.example.fitshare.Profile

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.fitshare.MainActivity
import com.example.fitshare.R
import com.example.fitshare.WorkOutPlan.PlanCollectionAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_work_out_plan.*

class ProfileDetailsFragment : Fragment() {
    var def: ColorStateList? = null
    var item1: TextView? = null
    var item2: TextView? = null
    var item3: TextView? = null
    var select: TextView? = null
    private var bottomAppBarVisibility = View.GONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity is MainActivity) {
            var  mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(bottomAppBarVisibility)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_profile_details, container, false)
        onClick(view)
        return view
    }


    private fun onClick(view: View) {
        item1 = view.findViewById(R.id.item1)
        item2 = view.findViewById(R.id.item2)
        item3 = view.findViewById(R.id.item3)
        item1!!.setOnClickListener{
            select!!.animate().x(0f).duration = 100
            item1?.setTextColor(Color.WHITE)
            item2!!.setTextColor(def)
            item3!!.setTextColor(def)
        }
        item2!!.setOnClickListener{
            item1!!.setTextColor(def)
            item2?.setTextColor(Color.WHITE)
            item3!!.setTextColor(def)
            val size = item2!!.width
            select!!.animate().x(size.toFloat()).duration = 100
        }
        item3!!.setOnClickListener {
            item1!!.setTextColor(def)
            item3?.setTextColor(Color.WHITE)
            item2!!.setTextColor(def)
            val size = item2!!.width * 2
            select!!.animate().x(size.toFloat()).duration = 100
        }
        select = view.findViewById(R.id.select)
        def = item2!!.textColors
    }
}