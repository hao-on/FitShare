package com.example.fitshare.Recipe

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.fitshare.R
import com.example.fitshare.fitApp
import kotlinx.android.synthetic.main.fragment_recipe_details.view.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import org.bson.types.ObjectId


class RecipeDetailsFragment : Fragment() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var partition: String
    private lateinit var recipeRealm: Realm

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_recipe_details, container, false)
        user = fitApp.currentUser()
        partition = "recipe"

        val config = SyncConfiguration.Builder(user!!, partition)
            .build()
        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                this@RecipeDetailsFragment.recipeRealm = realm

                var recipeName = arguments?.getString("recipeName")
                var recipeID = arguments?.getString("recipeID")

                Log.i("recipe", recipeID.toString())
                Log.i("recipe", recipeName.toString())

                view.tvName.text = recipeName
                val id = ObjectId(recipeID)

                var recName = recipeRealm.where<Recipe>().equalTo("_id", id).findFirst()
                Log.i("recipe", recName?.id.toString())

                if (recName != null) {
                    Toast.makeText(requireActivity().applicationContext,
                        "Name: " + recName.recipeName, Toast.LENGTH_SHORT).show()
                }
            }
        })




        val rBar: RatingBar = view.findViewById(R.id.rBar)
        if (rBar != null) {
            val button: Button = view.findViewById(R.id.btnRating)
            button?.setOnClickListener {
                val star = rBar.rating.toString()
                Toast.makeText(requireActivity().applicationContext,
                    "Rating is: " + star, Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
}