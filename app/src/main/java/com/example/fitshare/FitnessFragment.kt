package com.example.fitshare

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fitness_toolbar.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.Caloric.CaloricAdapter
import com.example.fitshare.Exercise.Exercise
import com.example.fitshare.Exercise.ExerciseAdapter
import com.example.fitshare.Food.Food
import com.example.fitshare.Food.FoodAdapter
import com.example.fitshare.Recipe.Recipe
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Case
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_fitness.*
import kotlinx.android.synthetic.main.layout_caloric.*



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FitnessFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FitnessFragment : Fragment()  {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fitnessRealm: Realm

    private lateinit var userRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var caloricAdapter: CaloricAdapter
    private lateinit var fab: FloatingActionButton
    private lateinit var partition: String
    //private lateinit var todayView: TextView
   // private lateinit var monthDayText: TextView
    private lateinit var tvRemaining: TextView
    private lateinit var tvFood: TextView
    private lateinit var tvGoal: TextView


    //private lateinit var myRecipe: AppCompatButton

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =inflater.inflate(R.layout.fragment_fitness, container, false)
        val calview: View =inflater.inflate(R.layout.layout_caloric, container, false)
        user = fitApp.currentUser()
        partition = "fitness"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()

        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@FitnessFragment.fitnessRealm = realm
                rvExercise.layoutManager =
                    LinearLayoutManager(requireActivity().applicationContext)
                rvExercise.setHasFixedSize(true)
                val exercises=realm.where<Exercise>().findAll()
                exerciseAdapter = ExerciseAdapter(exercises, user!!, partition)
                rvExercise.adapter = exerciseAdapter


                rvNutrition.layoutManager =
                    LinearLayoutManager(requireActivity().applicationContext)
                rvNutrition.setHasFixedSize(true)

                tvRemaining=calview.findViewById(R.id.tvRemaining)
                var remain= tvRemaining
                tvFood=calview.findViewById(R.id.tvFood)
                tvGoal=calview.findViewById(R.id.tvGoal)
                val goal= tvGoal.text.toString().toInt()

                val foods= realm.where<Food>().findAll()
                val foodSum=foods.sum("calories")


                remain.text=(goal-foodSum.toInt()).toString()

                foodAdapter = FoodAdapter(foods, user!!, partition)
                rvNutrition.adapter = foodAdapter

            }
        })

        val user_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .build()

        Realm.getInstanceAsync(user_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@FitnessFragment.userRealm = realm
            }
        })



        // Set a click listener for button widget

       /* monthDayText.setOnClickListener{
            // Initialize a new DatePickerFragment
            val newFragment = DatePickerFragment()
            // Show the date picker dialog
            newFragment.show(parentFragmentManager, "Date Picker")
        }*/

    return view
    }

    private fun recyclerExerciseUpdate(realm: Realm, user: io.realm.mongodb.User?, partition: String, text: String){
        exerciseAdapter = ExerciseAdapter(realm.where<Exercise>().contains("exerciseName", text, Case.INSENSITIVE)
            .findAll(), user!!, partition)
        rvExercise.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        rvExercise.adapter = exerciseAdapter
        rvExercise.setHasFixedSize(true)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FitnessFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FitnessFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}