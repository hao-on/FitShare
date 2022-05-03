package com.example.fitshare

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.Food.Food
import com.example.fitshare.Food.FoodAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import com.example.fitshare.User.User
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_fitness.*
import kotlinx.android.synthetic.main.layout_add_food.*
import kotlinx.android.synthetic.main.layout_caloric.*


class BottomDialogFood : BottomSheetDialogFragment() {
    private lateinit var foodRealm: Realm
    private lateinit var userRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var btnSubmit: Button
    private lateinit var tvRemaining: TextView
    private lateinit var tvFood: TextView
    private lateinit var tvGoal: TextView
    private lateinit var partition: String

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.layout_add_food, container, false)
        val mainview: View =inflater.inflate(R.layout.layout_caloric, container, false)

        user = fitApp.currentUser()
        partition = "fitness"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()

        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@BottomDialogFood.foodRealm = realm
            }
        })

        val user_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .build()

        Realm.getInstanceAsync(user_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@BottomDialogFood.userRealm = realm
            }
        })

        btnSubmit = view.findViewById(R.id.btnSubmitFood)


        tvRemaining=mainview.findViewById(R.id.tvRemaining)
        tvFood=mainview.findViewById(R.id.tvFood)
        tvGoal=mainview.findViewById(R.id.tvGoal)

        // adding on click listener for our button.
        btnSubmit.setOnClickListener {
            val food = Food(txtFd_Name.text.toString(),
                txtFd_Calories.text.toString().toDouble(),
                txtFd_Protein.text.toString().toDouble(),
                txtFd_Carbs.text.toString().toDouble(),
                txtFd_Fats.text.toString().toDouble(),
                user!!.id
            )

            foodRealm.executeTransactionAsync { realm -> realm.insert(food)

            }
            val calFood=tvFood.text.toString().toInt()+txtFd_Calories.text.toString().toInt()
            tvFood.setText(calFood.toString())
            val calRemaining=tvGoal.text.toString().toInt()-tvFood.text.toString().toInt() +tvRemaining.text.toString().toInt()

            tvRemaining.setText(calRemaining.toString())



            /*userRealm.executeTransactionAsync { transactionRealm: Realm ->
                // get a frog from the database to update
                val userData = transactionRealm.where(User::class.java).findFirst()
                userData?.foods?.add(food)
            }*/

            dialog?.dismiss()
            var fitnessFragment : Fragment = FitnessFragment()
            val bundle = Bundle()
            fitnessFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fitnessFragment).commit()

        }

        return view
    }

    companion object {
        fun newInstance(): BottomDialogFood {
            return BottomDialogFood()
        }
    }
}