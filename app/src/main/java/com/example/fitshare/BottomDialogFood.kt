package com.example.fitshare

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.annotation.Nullable
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


class BottomDialogFood : BottomSheetDialogFragment() {
    private lateinit var foodRealm: Realm
    private lateinit var userRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var btnSubmit: Button
    private lateinit var partition: String

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.layout_add_food, container, false)

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

        // adding on click listener for our button.
        btnSubmit.setOnClickListener {
            val food = Food(txtFd_Name.text.toString(),
                txtFd_Calories.text.toString().toDouble(),
                txtFd_Protein.text.toString().toDouble(),
                txtFd_Carbs.text.toString().toDouble(),
                txtFd_Fats.text.toString().toDouble())
            foodRealm.executeTransactionAsync { realm -> realm.insert(food) }


            /*userRealm.executeTransactionAsync { transactionRealm: Realm ->
                // get a frog from the database to update
                val userData = transactionRealm.where(User::class.java).findFirst()
                userData?.foods?.add(food)
            }*/

            dialog?.dismiss()
        }

        return view
    }

    companion object {
        fun newInstance(): BottomDialogFood {
            return BottomDialogFood()
        }
    }
}