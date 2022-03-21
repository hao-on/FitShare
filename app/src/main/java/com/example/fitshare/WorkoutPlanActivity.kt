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
import com.example.fitshare.Exercise.kt.Exercise
import com.example.fitshare.Recipe.Recipe
import com.mongodb.tasktracker.model.Plan_Core
import com.mongodb.tasktracker.model.Plan_Pull
import com.mongodb.tasktracker.model.Plan_Push
import kotlinx.android.synthetic.main.activity_add_recipe.*
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.mongodb.User
import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.MongoDatabase
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.workoutplan_main.*
import org.bson.Document
import org.bson.types.ObjectId

class WorkoutPlanActivity : AppCompatActivity() {
    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate (savedInstanceState)
        setContentView(R.layout.workoutplan_main)

        // get reference to button
        val pushbutton = findViewById(R.id.push_button) as Button
        val pullbutton = findViewById(R.id.pull_button) as Button
        val corebutton = findViewById(R.id.core_button) as Button
        var myTextView = findViewById(R.id.textView2) as TextView
        var myTextView2 = findViewById(R.id.textView3) as TextView
        var myTextView3 = findViewById(R.id.textView6) as TextView
        var plan_core = Plan_Core.coreplan
        var plan_push = Plan_Push.pushplan
        var plan_pull = Plan_Pull.pullplan
// set on-click listener
        corebutton.setOnClickListener {
            myTextView.text = plan_core.toString()
            Toast.makeText(this@WorkoutPlanActivity, "Plan for Core:",Toast.LENGTH_SHORT).show()
        }

        pushbutton.setOnClickListener {
            myTextView2.text = plan_push.toString()
            Toast.makeText(this@WorkoutPlanActivity, "Plan for Push:",Toast.LENGTH_SHORT).show()
        }

        pushbutton.setOnClickListener {
            myTextView.text = plan_pull.toString()
            Toast.makeText(this@WorkoutPlanActivity, "Plan for Pull:",Toast.LENGTH_SHORT).show()
        }

    }
}