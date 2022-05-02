package com.example.fitshare.Recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.Nullable
import com.example.fitshare.MainActivity
import com.example.fitshare.R
import com.example.fitshare.fitApp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.layout_add_recipe.*

class EditBottomDialog : BottomSheetDialogFragment() {
    private lateinit var recipeRealm: Realm
    private lateinit var userRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var btnSubmit: Button
    private lateinit var partition: String
    private var removeNavBar = View.GONE

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        if (activity is MainActivity){
            var mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(removeNavBar)
        }
    }
    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.layout_edit_recipe, container, false)

        user = fitApp.currentUser()
        partition = "recipe"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()

        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@EditBottomDialog.recipeRealm = realm
            }
        })

//        val user_config : SyncConfiguration =
//            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
//                .build()

//        Realm.getInstanceAsync(user_config, object: Realm.Callback() {
//            override fun onSuccess(realm: Realm) {
//                // since this realm should live exactly as long as this activity, assign the realm to a member variable
//                this@EditBottomDialog.userRealm = realm
//            }
//        })

        btnSubmit = view.findViewById(R.id.btnSubmitRecipe)

        // adding on click listener for our button.
        btnSubmit.setOnClickListener {
            val recipe = Recipe(txtRec_Name.text.toString(),
                txtRec_Descr.text.toString(),
                txtRec_Ingr.text.toString(),
                txtRec_Steps.text.toString(),
                txtRec_Time.text.toString(),
                user?.id.toString())

//            userRealm.executeTransactionAsync { transactionRealm: Realm ->
//                val userData = transactionRealm.where(User::class.java).findFirst()
//                userData?.recipes?.add(recipe)
//                transactionRealm.insertOrUpdate(userData)
//            }

            recipeRealm.executeTransactionAsync { transactionRealm: Realm ->
                transactionRealm.insert(recipe) }

            dialog?.dismiss()
        }

        return view
    }

    companion object {
        fun newInstance(): BottomDialog {
            return BottomDialog()
        }
    }
}