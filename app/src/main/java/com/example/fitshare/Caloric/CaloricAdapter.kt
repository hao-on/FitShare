package com.example.fitshare.Caloric

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

class CaloricAdapter(data: OrderedRealmCollection<Caloric>,
                      val user: io.realm.mongodb.User,
                      private val partition: String
) : RealmRecyclerViewAdapter<Caloric, CaloricAdapter.CaloricViewHolder?>
    (data, true) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CaloricViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.layout_caloric, parent, false)
        return CaloricViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CaloricViewHolder, position: Int) {
        val obj: Caloric? = getItem(position)
        holder.data = obj
        holder.goal.text = obj?.goal.toString()
        holder.food.text = obj?.food.toString()
        holder.exercise.text = obj?.exercise.toString()
        holder.remaining.text = obj?.remaining.toString()

    }

    inner class CaloricViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var goal: TextView = view.findViewById(R.id.tvExerciseName)
        var food: TextView = view.findViewById(R.id.tvSets)
        var exercise: TextView = view.findViewById(R.id.tvReps)
        var remaining: TextView = view.findViewById(R.id.tvRemaining)

        var data: Caloric? = null
    }
}