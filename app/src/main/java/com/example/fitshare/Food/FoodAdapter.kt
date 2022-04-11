package com.example.fitshare.Food

import android.app.AlertDialog
import android.content.Context
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.Food.Food
import com.example.fitshare.R

import com.example.fitshare.Food.FoodAdapter
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import org.bson.types.ObjectId

class FoodAdapter(data: OrderedRealmCollection<Food>,
                      val user: io.realm.mongodb.User,
                      private val partition: String
) : RealmRecyclerViewAdapter<Food, FoodAdapter.FoodViewHolder?>
    (data, true) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): FoodViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.layout_food, parent, false)
        return FoodViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val obj: Food? = getItem(position)
        holder.data = obj

        holder.foodName.text = obj?.foodName
        holder.calories.text = "Calories: "+obj?.calories.toString()+" kal"
        holder.protein.text = "Protein: "+obj?.protein.toString()+" g"
        holder.carbs.text = "Carbs: "+obj?.carbs.toString()+" g"
        holder.fats.text = "Fats: "+obj?.fats.toString()+" g"

    }

    inner class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var foodName: TextView = view.findViewById(R.id.tvFoodName)
        var calories: TextView = view.findViewById(R.id.tvCalories)
        var protein: TextView = view.findViewById(R.id.tvProtein)
        var carbs: TextView = view.findViewById(R.id.tvCarbs)
        var fats: TextView = view.findViewById(R.id.tvFats)
        var data: Food? = null



    }
}