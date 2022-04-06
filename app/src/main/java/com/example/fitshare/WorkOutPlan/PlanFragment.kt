package com.example.fitshare.WorkOutPlan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.Exercise.kt.Exercise
import com.example.fitshare.Exercise.kt.ExerciseAdapter
import com.example.fitshare.R
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.fitApp
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_plan.*
import kotlinx.android.synthetic.main.fragment_plan.view.*
import kotlinx.android.synthetic.main.fragment_recipe.*
import kotlinx.android.synthetic.main.fragment_recipe_details.view.*
import kotlinx.android.synthetic.main.layout_plan.*
import org.bson.types.ObjectId
import java.util.*

class PlanFragment : Fragment() {
    private lateinit var planRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var partition: String
    private lateinit var plan: String
    private lateinit var adapter: ExerciseAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plan = arguments?.getString("planName").toString()

        user = fitApp.currentUser()
        partition = plan

        val config = SyncConfiguration.Builder(user!!, partition)
            .build()

        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                this@PlanFragment.planRealm = realm
                adapter = ExerciseAdapter(planRealm.where<Exercise>()
                    .sort("exerciseName").findAll(), user!!, partition)
                rvPlan.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
                rvPlan.adapter = adapter
                rvPlan.setHasFixedSize(true)


            }
        })
    }

}