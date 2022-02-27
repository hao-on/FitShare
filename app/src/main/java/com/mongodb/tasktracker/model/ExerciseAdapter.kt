package com.mongodb.tasktracker.model

import android.app.AlertDialog
import android.content.Context
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.mongodb.tasktracker.R
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import org.bson.types.ObjectId

internal class ExerciseAdapter(data: OrderedRealmCollection<Exercise>, val user: io.realm.mongodb.User, private val partition: String) : RealmRecyclerViewAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder?>(data, true) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseAdapter.ExerciseViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.task_view, parent, false)
        return ExerciseViewHolder(itemView)
    }

}