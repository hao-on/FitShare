package com.example.fitshare.WorkOutPlan

import com.example.fitshare.Exercise.kt.Exercise
import java.lang.ref.WeakReference

object ShoppingCart {
    // 1.
    @JvmStatic
    var exercises: List<Exercise> = emptyList()
        private set

    private var onCartChangedListener: WeakReference<OnCartChangedListener>? = null

    fun setOnCartChangedListener(listener: OnCartChangedListener) {
        this.onCartChangedListener = WeakReference(listener)
    }

    private fun notifyCartChanged() {
        onCartChangedListener?.get()?.onCartChanged()
    }

    // 2.
    fun addExercise(exercise: Exercise) {
        exercises = exercises + listOf(exercise)
        notifyCartChanged()
    }

    fun removeExercise(exercise: Exercise) {
        exercises = exercises - listOf(exercise)
        notifyCartChanged()
    }

    // 3.
    fun clear() {
        exercises = emptyList()
        notifyCartChanged() // New
    }

    interface OnCartChangedListener {
        fun onCartChanged()
    }


}