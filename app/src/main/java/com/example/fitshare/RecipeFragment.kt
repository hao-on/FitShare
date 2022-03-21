package com.example.fitshare

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.User.User
import com.example.fitshare.Recipe.RecipeAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_recipe.*
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.Helper.MyButton
import com.example.fitshare.Helper.MySwipeHelper
import com.example.fitshare.Listener.MyButtonClickListener
import io.realm.Case
import io.realm.RealmList
import io.realm.RealmResults
import kotlinx.android.synthetic.main.layout_add_recipe.*
import java.util.*





// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recipeRealm: Realm
    private lateinit var userRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var adapter: RecipeAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var partition: String
    private lateinit var searchview: SearchView
    private lateinit var myRecipe: AppCompatButton
    private lateinit var allRecipe: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_recipe, container, false)
        user = fitApp.currentUser()
        partition = "recipe"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()

        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@RecipeFragment.recipeRealm = realm
                rvRecipe.layoutManager =
                    LinearLayoutManager(requireActivity().applicationContext)
                rvRecipe.setHasFixedSize(true)
                adapter = RecipeAdapter(realm.where<Recipe>().sort("recipeName").findAll(), user!!, partition)
                rvRecipe.adapter = adapter
            }
        })

        val user_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .build()

        Realm.getInstanceAsync(user_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@RecipeFragment.userRealm = realm
            }
        })

        searchview = view.findViewById(R.id.searchView)
        searchview.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(text: String): Boolean {
                recyclerSearch(recipeRealm, user, partition, text)
                return false
            }
            override fun onQueryTextChange(text: String): Boolean {
                recyclerSearch(recipeRealm, user, partition, text)
                return false
            }
        })

        myRecipe = view.findViewById(R.id.myRecipeFilter)
        myRecipe.setOnClickListener {
            //val userData = userRealm.where<User>().findFirst()
            //val recipeList: RealmList<Recipe>? = userData?.recipes
            //val results: RealmResults<Recipe> = userData?.recipes?.where()!!.findAll()

            adapter = RecipeAdapter(recipeRealm.where<Recipe>().contains("user_id", user?.id.toString())
                .findAll(), user!!, partition)
            rvRecipe.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            rvRecipe.adapter = adapter
            rvRecipe.setHasFixedSize(true)

            val swipe = object : MySwipeHelper(requireActivity().applicationContext, rvRecipe, 150){
                override fun instantiateMyButton(
                    viewHolder: RecyclerView.ViewHolder,
                    buffer: MutableList<MyButton>
                ) {
                    buffer.add(MyButton(requireActivity().applicationContext,
                        "Delete",
                        30,
                        0,
                        Color.parseColor("#FF3C30"),
                        object: MyButtonClickListener{
                            override fun onClick(pos: Int) {
                                Toast.makeText(requireActivity().applicationContext, "Delete Button Clicked", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ))

                    buffer.add(MyButton(requireActivity().applicationContext,
                        "Modify",
                        30,
                        0,
                        Color.parseColor("#FF9502"),
                        object: MyButtonClickListener{
                            override fun onClick(pos: Int) {
                                Toast.makeText(requireActivity().applicationContext, "Modify Button Clicked", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ))
                }
            }
        }

        allRecipe = view.findViewById(R.id.allFilter)
        allRecipe.setOnClickListener {
            rvRecipe.layoutManager =
                LinearLayoutManager(requireActivity().applicationContext)
            rvRecipe.setHasFixedSize(true)
            adapter = RecipeAdapter(recipeRealm.where<Recipe>().sort("recipeName").findAll(), user!!, partition)
            rvRecipe.adapter = adapter
        }
        return view
    }

    private fun recyclerSearch(realm: Realm, user: io.realm.mongodb.User?, partition: String, text: String){
        adapter = RecipeAdapter(realm.where<Recipe>().contains("recipeName", text, Case.INSENSITIVE)
            .findAll(), user!!, partition)
        rvRecipe.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        rvRecipe.adapter = adapter
        rvRecipe.setHasFixedSize(true)
    }


    override fun onStop() {
        super.onStop()
        user.run {
            recipeRealm.close()
            userRealm.close()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecipeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecipeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

