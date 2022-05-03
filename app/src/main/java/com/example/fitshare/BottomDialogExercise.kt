package com.example.fitshare

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.Exercise.Exercise
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import com.example.fitshare.User.User
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_fitness.*
import kotlinx.android.synthetic.main.layout_add_exercise.*
import java.util.*


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
                Toast.makeText(requireActivity().applicationContext, "Realm Connected", Toast.LENGTH_SHORT).show()
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
        btnSubmitExercise = view.findViewById(R.id.btnSubmitExercise)
        btnSubmitExercise.setOnClickListener {
            val exercise = Exercise(
                txtEx_Name.text.toString(),
                txtEx_Reps.text.toString().toLong(),
                txtEx_Sets.text.toString().toLong(),
                txtEx_Weight.text.toString().toDouble(),
                user!!.id
            )
            exerciseRealm.executeTransactionAsync { realm ->
                realm.insert(exercise) }


            userRealm.executeTransactionAsync { transactionRealm: Realm ->
                transactionRealm.insert(exercise)
                val userData = transactionRealm.where(User::class.java).findFirst()
                userData?.exercises?.add(exercise)
            }

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