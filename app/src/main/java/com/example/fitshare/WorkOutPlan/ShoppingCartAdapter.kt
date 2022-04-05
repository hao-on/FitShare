package com.example.fitshare.WorkOutPlan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.Exercise.kt.Exercise
import com.example.fitshare.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_shopping_cart.view.*
import kotlinx.android.synthetic.main.layout_plan.view.*

class ShoppingCartAdapter : ListAdapter<Exercise, ShoppingCartAdapter.ExerciseViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ExerciseViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_plan, parent, false)
        itemView.btnCartFunc.setImageResource(R.drawable.ic_remove)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val obj: Exercise? = getItem(position)
        holder.data = obj
        holder.exerciseName.text = obj?.exerciseName
        holder.sets.text = obj?.sets.toString()
        holder.reps.text = obj?.reps.toString()
        holder.weight.text = obj?.weight.toString()

        holder.removeFromCart.setOnClickListener {
            if (obj != null) {
                ShoppingCart.removeExercise(obj)
            }
            Toast.makeText(it.context,
                "${obj?.exerciseName} removed from cart", Toast.LENGTH_SHORT).show()
        }
    }

    inner class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var exerciseName: TextView = view.findViewById(R.id.tvExerciseName)
        var sets: TextView = view.findViewById(R.id.tvSets)
        var reps: TextView = view.findViewById(R.id.tvReps)
        var weight: TextView = view.findViewById(R.id.tvWeight)
        //var date: TextView = view.findViewById(R.id.tvDate)
        var data: Exercise? = null
        var removeFromCart: FloatingActionButton = view.findViewById(R.id.btnCartFunc)
    }

    private companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Exercise>() {
            override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise) =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise) =
                oldItem == newItem
        }
    }
}
