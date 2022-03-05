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


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseAdapter.ExerciseViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.task_view, parent, false)
        return ExerciseViewHolder(itemView)
    }

    /*override fun onBindViewHolder(holder: RecipeAdapter.RecipeViewHolder, position: Int) {
        val obj: Exercise? = getItem(position)
        holder.data = obj
        holder.exerciseName.text = obj?.exerciseName
        holder.sets.text = obj?.sets
        holder.reps.text = obj?.reps
        holder.weight.text = obj?.weight
        holder.userid.text=obj?.userid
        holder.date.text=obj?.date

        // multiselect popup to control status
        holder.itemView.setOnClickListener {
            run {
                val popup = PopupMenu(holder.itemView.context, holder.menu)
                val menu = popup.menu

                // add a delete button to the menu, identified by the delete code
                val deleteCode = -1
                menu.add(0, deleteCode, Menu.NONE, "Delete Recipe")

                val updateCode = 1
                menu.add(0, updateCode, Menu.NONE,  "Modify Recipe")

                // handle clicks for each button based on the code the button passes the listener
                popup.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item!!.itemId) {
                        deleteCode -> {
                            removeAt(holder.data?.id!!)
                        }
                        updateCode -> {
                            updateAt(holder.itemView.context, holder.data?.id!!)
                        }
                    }

                    true
                }
                popup.show()
            }
        }*/

    }


}