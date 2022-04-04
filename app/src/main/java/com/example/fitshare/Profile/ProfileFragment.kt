package com.example.fitshare.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.res.ColorStateList
import android.graphics.Color
import com.example.fitshare.R


class ProfileFragment : Fragment() {
    var def: ColorStateList? = null
    var item1: TextView? = null
    var item2: TextView? = null
    var item3: TextView? = null
    var select: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_profile, container, false)
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
        return view
    }

//    fun onClick(view: View) {
//        if (view.id == R.id.item1) {
//            select!!.animate().x(0f).duration = 100
//            item1?.setTextColor(Color.WHITE)
//            item2!!.setTextColor(def)
//            item3!!.setTextColor(def)
//        } else if (view.id == R.id.item2) {
//            item1!!.setTextColor(def)
//            item2?.setTextColor(Color.WHITE)
//            item3!!.setTextColor(def)
//            val size = item2!!.width
//            select!!.animate().x(size.toFloat()).duration = 100
//        } else if (view.id == R.id.item3) {
//            item1!!.setTextColor(def)
//            item3?.setTextColor(Color.WHITE)
//            item2!!.setTextColor(def)
//            val size = item2!!.width * 2
//            select!!.animate().x(size.toFloat()).duration = 100
//        }
//    }
}