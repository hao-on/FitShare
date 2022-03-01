package com.mongodb.tasktracker.model

import android.view.*
import com.mongodb.tasktracker.R
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

internal class ExerciseAdapter(data: OrderedRealmCollection<Exercise>, val user: io.realm.mongodb.User, private val partition: String) : RealmRecyclerViewAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder?>(data, true) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseAdapter.ExerciseViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.task_view, parent, false)
        return ExerciseViewHolder(itemView)
    }

    class ExerciseViewHolder(itemView: View) {

    }

    override fun onBindViewHolder(holder: ExerciseAdapter.ExerciseViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}