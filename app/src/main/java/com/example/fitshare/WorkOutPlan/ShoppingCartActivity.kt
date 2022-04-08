package com.example.fitshare.WorkOutPlan

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.Exercise.kt.Exercise
import com.example.fitshare.Exercise.kt.ExerciseAdapter
import com.example.fitshare.R
import com.example.fitshare.fitApp
import io.realm.OrderedRealmCollection
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlinx.android.synthetic.main.fragment_plan.*
import kotlinx.android.synthetic.main.layout_plan.view.*

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var exercises: List<Exercise>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        setupExercises()
        setupRecyclerView()
        setupClearCartButton()
        ShoppingCart.setOnCartChangedListener(onCartChangedListener)
    }

    private var onCartChangedListener = object : ShoppingCart.OnCartChangedListener{
        override fun onCartChanged() {
            setupExercises()
            setupRecyclerView()
        }
    }

    private fun setupExercises() {
        exercises = ShoppingCart.exercises
    }

    private fun setupRecyclerView() {
        val adapter = ShoppingCartAdapter()
        adapter.submitList(exercises)
        rvCart.layoutManager = LinearLayoutManager(this)
        rvCart.adapter = adapter
        rvCart.setHasFixedSize(true)
    }

    private fun setupClearCartButton() {
        btnClear.setOnClickListener {
            ShoppingCart.clear()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ShoppingCartActivity::class.java)
        }
    }
}