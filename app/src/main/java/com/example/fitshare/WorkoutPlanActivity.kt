package com.example.fitshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.Exercise.kt.Exercise
import com.example.fitshare.Exercise.kt.ExerciseAdapter
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.Recipe.RecipeAdapter
import com.example.fitshare.User.User
import com.mongodb.tasktracker.model.Plan_Core
import kotlinx.android.synthetic.main.activity_add_recipe.*
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_recipe.*
import kotlinx.android.synthetic.main.layout_add_recipe.*
import kotlinx.android.synthetic.main.workoutplan_main.*

class WorkoutPlanActivity : AppCompatActivity() {
    private lateinit var exerciseRealm: Realm
    private lateinit var planRealm: Realm
    private lateinit var userRealm: Realm
    private lateinit var partition: String
    private lateinit var partition1: String
    private lateinit var partition2: String
    private lateinit var partition3: String
    //private lateinit var partition4: Int
    private lateinit var adapter: ExerciseAdapter
    private var user: io.realm.mongodb.User? = null

    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate (savedInstanceState)
        setContentView(R.layout.workoutplan_main)



        user = fitApp.currentUser()
        partition = "plan_core"
        partition1 = "plan_push"
        partition2 = "plan_pull"
        partition3 ="plan_leg"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()
        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@WorkoutPlanActivity.exerciseRealm = realm
            }
        })

        val user_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .build()
        Realm.getInstanceAsync(user_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@WorkoutPlanActivity.userRealm = realm
            }
        })


         //set on-click listener
        planCore.setOnClickListener {

            val exercise1 = Exercise(
                "Crunches",
                4,
                20,
                0.0)
            val exercise2 = Exercise(
                "Russian Twist",
                3,
                15,
                0.0)
            val exercise3 = Exercise(
                "Leg Lifts",
                3,
                15,
                0.0)
            val exercise4 = Exercise(
                "Mountain Climbers",
                3,
                15,
                0.0)

            ratingBar.rating = 0.0f
            ratingBar.stepSize = 1.0f
            ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                Toast.makeText(this, "You gave this plan a rating of: $rating", Toast.LENGTH_SHORT).show()
            }
            planRealm.executeTransactionAsync { realm ->
                realm.insert(exercise1)
                realm.insert(exercise2)
                realm.insert(exercise3)
                realm.insert(exercise4)
            }

            rvPlan.layoutManager = LinearLayoutManager(this)
            rvPlan.setHasFixedSize(true)
            adapter = ExerciseAdapter(exerciseRealm.where<Exercise>()
                .sort("exerciseName").findAll(), user!!, partition)
            rvPlan.adapter = adapter
        }
        planPush.setOnClickListener {

            val exercise1 = Exercise(
                "Bench Press",
                3,
                15,
                0.0)
            val exercise2 = Exercise(
                "Shoulder Press",
                4,
                10,
                0.0)
            val exercise3 = Exercise(
                "Incline Bench Press",
                3,
                10,
                0.0)
            val exercise4 = Exercise(
                "Decline Bench Press",
                3,
                15,
                0.0)
            val exercise5 = Exercise(
                "Chest Press Machine",
                3,
                10,
                0.0)

            ratingBar.rating = 0.0f
            ratingBar.stepSize = 1.0f
            ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                Toast.makeText(this, "You gave this plan a rating of: $rating", Toast.LENGTH_SHORT).show()
            }

            planRealm.executeTransactionAsync { realm ->
                realm.insert(exercise1)
                realm.insert(exercise2)
                realm.insert(exercise3)
                realm.insert(exercise4)
                realm.insert(exercise5)
            }

            rvPlan.layoutManager = LinearLayoutManager(this)
            rvPlan.setHasFixedSize(true)
            adapter = ExerciseAdapter(exerciseRealm.where<Exercise>()
                .sort("exerciseName").findAll(), user!!, partition)
            rvPlan.adapter = adapter
        }
        planPull.setOnClickListener {

            val exercise1 = Exercise(
                "Bicep Curl",
                3,
                15,
                0.0)
            val exercise2 = Exercise(
                "Latisimus Pull Down",
                4,
                10,
                0.0)
            val exercise3 = Exercise(
                "Bent-over Row",
                3,
                15,
                0.0)

            ratingBar.rating = 0.0f
            ratingBar.stepSize = 1.0f
            ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                Toast.makeText(this, "You gave this plan a rating of: $rating", Toast.LENGTH_SHORT).show()
            }

            planRealm.executeTransactionAsync { realm ->
                realm.insert(exercise1)
                realm.insert(exercise2)
                realm.insert(exercise3)
            }

            rvPlan.layoutManager = LinearLayoutManager(this)
            rvPlan.setHasFixedSize(true)
            adapter = ExerciseAdapter(exerciseRealm.where<Exercise>().sort("exerciseName").findAll(), user!!, partition)
            rvPlan.adapter = adapter
        }

        planPull.setOnClickListener {

            val exercise1 = Exercise(
                "Quad Extensions",
                3,
                15,
                0.0)
            val exercise2 = Exercise(
                "Squats",
                4,
                10,
                0.0)
            val exercise3 = Exercise(
                "Calf Raises",
                3,
                15,
                0.0)
            val exercise4 = Exercise(
                "Hurdles",
                3,
                15,
                0.0)
            planRealm.executeTransactionAsync { realm ->
                realm.insert(exercise1)
                realm.insert(exercise2)
                realm.insert(exercise3)
                realm.insert(exercise4)
            }

            ratingBar.rating = 0.0f
            ratingBar.stepSize = 1.0f
            ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                Toast.makeText(this, "You gave this plan a rating of: $rating", Toast.LENGTH_SHORT).show()
            }

            rvPlan.layoutManager = LinearLayoutManager(this)
            rvPlan.setHasFixedSize(true)
            adapter = ExerciseAdapter(exerciseRealm.where<Exercise>().sort("exerciseName").findAll(), user!!, partition)
            rvPlan.adapter = adapter
        }


    }
}

/*
@Override
protected void onCreate(Bundle savedInstance){

    Button rbutton;
    RatingBar ratingBar;

    rbutton = findViewByID(R.id.rbutton);
    ratingBar = findViewById(R.id.ratingBar);

    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingChangeListener() {
        @Override

        public void OnRatingChanged(RatingBar ratingBar, float v, boolean b){

            int rating = (int) v;
            String message = null;

            switch (rating) {
                case 1:
                message = "You gave this plan a 1 star rating"

                case 2:
                message = "You gave this plan a 2 star rating"

                case 3:
                message = "You gave this plan a 3 star rating"

                case 4:
                message = "You gave this plan a 4 star rating"

                case 5:
                message = "You gave this plan a 5 star rating"



            }

        }
    }


}
*/