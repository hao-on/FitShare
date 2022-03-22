package com.example.fitshare
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.Exercise.kt.Exercise
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.Recipe.RecipeAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mongodb.tasktracker.model.Plan_Core
import com.mongodb.tasktracker.model.Plan_Pull
import com.mongodb.tasktracker.model.Plan_Push
import kotlinx.android.synthetic.main.activity_add_recipe.*
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.MongoDatabase
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_recipe.*
import kotlinx.android.synthetic.main.layout_add_exercise.*
import kotlinx.android.synthetic.main.layout_add_recipe.*
import kotlinx.android.synthetic.main.workoutplan_main.*
import org.bson.Document
import org.bson.types.ObjectId

class WorkoutPlanActivity : AppCompatActivity() {
    private lateinit var planRealm: Realm
    private lateinit var userRealm: Realm
    private lateinit var partition: String

    private var user: io.realm.mongodb.User? = null

    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate (savedInstanceState)
        setContentView(R.layout.workoutplan_main)

        user = fitApp.currentUser()
        partition="plan"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()

        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@WorkoutPlanActivity.planRealm = realm
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

        // get reference to button
        /*
        var myTextView = findViewById(R.id.textView2) as TextView
        var myTextView2 = findViewById(R.id.textView3) as TextView
        var myTextView3 = findViewById(R.id.textView6) as TextView
         */
//        var plan_core = Plan_Core.coreplan
//      var plan_push = Plan_Push.pushplan
//      var plan_pull = Plan_Pull.pullplan



// set on-click listener
        core_button.setOnClickListener {
            /*
            textView2.text = plan_core.toString()
            //Toast.makeText(this@WorkoutPlanActivity, "Plan for Core:",Toast.LENGTH_SHORT).show()
            // call the array list of work outs and use the specific exercise
            // exercise should be multiple work outs not only one like the current impl
            // but add  one new exercise to our plan core diary
            // and then update that list for that user rather then adding one item
            /*
            plan core = [] no work outs
            1)
            add workout via click of the button
            create and exercise obj
            then get the old list -> []
            then add to old list [EX1]
            then update current PlanCore =[EX1]

            repeat for each click of button
             */
            plan_core.list.add(Exercise(
                "Crunches",
                3,
                15,
                0.0))
            plan_core.list.add(Exercise(
                "Russian Twist",
                3,
                15,
                0.0))
            plan_core.list.add(Exercise(
                "Leg Lifts",
                3,
                15,
                0.0))
                exerciseRealm.executeTransactionAsync { realm -> realm.insert(plan_core.list) }


                userRealm.executeTransactionAsync { transactionRealm: Realm ->
                    // get a frog from the database to update
                    val userData = transactionRealm.where(com.example.fitshare.User.User::class.java).findFirst()
                    userData?.exercises?.addAll(plan_core.list)
                }*/
            val exercise1 = Exercise(
                "Crunches",
                3,
                15,
                0.0)
            val exercise2 = Exercise(
                "Russian Twist",
                3,
                15,
                0.0)
            planRealm.executeTransactionAsync { realm ->
                realm.insert(exercise1)
                realm.insert(exercise2)
            }
        }
//
//        push_button.setOnClickListener {
//            textView3.text = plan_push.toString()
//            //Toast.makeText(this@WorkoutPlanActivity, "Plan for Push:",Toast.LENGTH_SHORT).show()
//            plan_push.list.add(Exercise(
//                "Bench Press",
//                4,
//                10,
//                135.0))
//            plan_push.list.add(Exercise(
//                "Shoulder Press",
//                3,
//                15,
//                30.0))
//            plan_push.list.add(Exercise(
//                "Incline Bench Press",
//                4,
//                10,
//                95.0))
//            exerciseRealm.executeTransactionAsync { realm -> realm.insert(plan_push.list) }
//
//
//            userRealm.executeTransactionAsync { transactionRealm: Realm ->
//                // get a frog from the database to update
//                val userData = transactionRealm.where(com.example.fitshare.User.User::class.java).findFirst()
//                userData?.exercises?.addAll(plan_core.list)
//            }
//        }
//
//        pull_button.setOnClickListener {
//           textView6.text = plan_pull.toString()
//            //Toast.makeText(this@WorkoutPlanActivity, "Plan for Pull:",Toast.LENGTH_SHORT).show()
//            plan_pull.list.add(Exercise(
//                "Bicep Curl",
//                3,
//                15,
//                15.0))
//            plan_pull.list.add(Exercise(
//                "Lat Pull-Down",
//                3,
//                10,
//                100.0))
//            plan_pull.list.add(Exercise(
//                "Rows",
//                3,
//                15,
//                70.0))
//            exerciseRealm.executeTransactionAsync { realm -> realm.insert(plan_pull.list) }
//
//
//            userRealm.executeTransactionAsync { transactionRealm: Realm ->
//                // get a frog from the database to update
//                val userData = transactionRealm.where(com.example.fitshare.User.User::class.java).findFirst()
//                userData?.exercises?.addAll(plan_core.list)
//            }
//        }

    }
}