package com.example.fitshare

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.fitshare.Exercise.kt.Exercise
import com.example.fitshare.Recipe.RecipeAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import com.example.fitshare.User.User
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_fitness.*
import kotlinx.android.synthetic.main.layout_add_exercise.*



class BottomDialogExercise : BottomSheetDialogFragment() {
    private lateinit var exerciseRealm: Realm
    private lateinit var userRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var btnSubmitExercise: Button
    private lateinit var partition: String

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.layout_add_exercise, container, false)

        user = fitApp.currentUser()
        partition = "fitness"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()

        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@BottomDialogExercise.exerciseRealm = realm
            }
        })

        val user_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .build()

        Realm.getInstanceAsync(user_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@BottomDialogExercise.userRealm = realm
            }
        })



        // adding on click listener for our button.
        btnSubmitExercise.setOnClickListener {
            val exercise= Exercise(
                txtEx_Name.text.toString(),
                txtEx_Reps.text.toString().toInt(),
                txtEx_Sets.text.toString().toInt(),
                txtEx_Weight.text.toString().toDouble())
            exerciseRealm.executeTransactionAsync { realm -> realm.insert(exercise) }


            /*userRealm.executeTransactionAsync { transactionRealm: Realm ->
                // get a frog from the database to update
                val userData = transactionRealm.where(User::class.java).findFirst()
                userData?.exercises?.add(exercise)
            }*/

            dialog?.dismiss()
        }

        return view
    }

    companion object {
        fun newInstance(): BottomDialogExercise {
            return BottomDialogExercise()
        }
    }
}