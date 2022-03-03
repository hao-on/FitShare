package com.example.fitshare

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.Recipe.RecipeAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_recipe.*
import android.R.attr.button
import android.R.attr
import android.app.SearchManager
import android.content.Context
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import io.realm.Case
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
    private var user: User? = null
    private lateinit var adapter: RecipeAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var partition: String

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
        return view
    }

    private fun recyclerSearch(realm: Realm, user: User?, partition: String, text: String){
        adapter = RecipeAdapter(realm.where<Recipe>().contains("recipeName", text, Case.INSENSITIVE)
            .findAll(), user!!, partition)
        rvRecipe.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        rvRecipe.adapter = adapter
        rvRecipe.setHasFixedSize(true)
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setMaxWidth(Int.MAX_VALUE)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(text: String): Boolean {
                recyclerSearch(projectRealm, user, partition, text)
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                recyclerSearch(projectRealm, user, partition, text)
                return false
            }
        })

        return true
    }*/

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

