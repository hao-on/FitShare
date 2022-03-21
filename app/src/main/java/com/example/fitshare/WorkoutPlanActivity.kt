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
import com.example.fitshare.Recipe.Recipe
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
        val corebutton = findViewById(R.id.core_button) as Button
        var myTextView = findViewById(R.id.textView) as TextView
        var plan_core = ""
// set on-click listener
        corebutton.setOnClickListener {
            myTextView.text = ""
            Toast.makeText(this@WorkoutPlanActivity, "Plan for core:",Toast.LENGTH_SHORT).show()
        }

    }
}