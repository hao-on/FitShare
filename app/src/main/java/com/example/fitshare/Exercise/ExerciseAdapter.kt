package com.example.fitshare.Exercise

import android.app.AlertDialog
import android.content.Context
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.R
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.Recipe.RecipeAdapter
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import org.bson.types.ObjectId

class ExerciseAdapter(data: OrderedRealmCollection<Exercise>,
                    val user: io.realm.mongodb.User,
                    private val partition: String
) : RealmRecyclerViewAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder?>
    (data, true) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ExerciseViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.layout_exercise, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val obj: Exercise? = getItem(position)
        holder.data = obj
        holder.exerciseName.text =obj?.exerciseName
        holder.sets.text = "Sets: "+ obj?.sets.toString()
        holder.reps.text = "Reps: "+obj?.reps.toString()
        holder.weight.text = "Weight: "+ obj?.weight.toString()+" lbs"
        //holder.date.text = obj?.date.toString()
    }

    inner class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var exerciseName: TextView = view.findViewById(R.id.tvExerciseName)
        var sets: TextView = view.findViewById(R.id.tvSets)
        var reps: TextView = view.findViewById(R.id.tvReps)
        var weight: TextView = view.findViewById(R.id.tvWeight)
        //var date: TextView = view.findViewById(R.id.tvDate)
        var data: Exercise? = null

    }
}