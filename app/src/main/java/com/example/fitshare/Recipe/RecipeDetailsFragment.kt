package com.example.fitshare.Recipe

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.fitshare.MainActivity
import com.example.fitshare.R
import com.example.fitshare.fitApp
import kotlinx.android.synthetic.main.fragment_recipe_details.view.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import org.bson.types.ObjectId


class RecipeDetailsFragment : Fragment() {
    private var user: io.realm.mongodb.User? = null
    private lateinit var partition: String
    private lateinit var recipeRealm: Realm
    private var removeNavBar = View.GONE
    private lateinit var editBtn: FloatingActionButton
    private lateinit var recipeID2: String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        if (activity is MainActivity){
            var mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(removeNavBar)
        }
    }
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
                var recipeID = arguments?.getString("recipeID")
                var recipe = recipeRealm.where<Recipe>().equalTo("_id", ObjectId(recipeID)).findFirst()
                recipeID2 = recipe?.id.toString()
                view.tvName.text = recipe?.recipeName
                view.tvDescription.text = recipe?.description
                view.tvPrepTime.text = recipe?.prepTime + " hrs"
                view.tvIngredients.text = recipe?.ingredients
                view.tvSteps.text = recipe?.steps

                if(recipe?.user_id.toString() != user?.id.toString()){
                    editBtn.hide()
                }else{
                    editBtn.show()
                }
//                if (recName != null) {
//                    Toast.makeText(requireActivity().applicationContext,
//                        "Name: " + recName.recipeName, Toast.LENGTH_SHORT).show()
//                }
            }
        })

        editBtn = view.findViewById(R.id.editRecipeBtn)
        editBtn.setOnClickListener{
            val editRecipeDialog : EditBottomDialog  = EditBottomDialog.newInstance()
            val bundle = Bundle()
            bundle.putString("recipeID2", recipeID2)
            editRecipeDialog.arguments = bundle
            editRecipeDialog.show(parentFragmentManager, null)
        }

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