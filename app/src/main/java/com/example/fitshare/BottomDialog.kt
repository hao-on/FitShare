package com.example.fitshare

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.Recipe.RecipeAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import com.example.fitshare.User.User
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.fragment_recipe.*
import kotlinx.android.synthetic.main.layout_add_recipe.*


class BottomDialog : BottomSheetDialogFragment() {
    private lateinit var recipeRealm: Realm
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

        val view: View = inflater.inflate(R.layout.layout_add_recipe, container, false)

        user = fitApp.currentUser()
        partition = "recipe"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()

        Realm.getInstanceAsync(config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@BottomDialog.recipeRealm = realm
            }
        })

        val user_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "user=${user!!.id}")
                .build()

        Realm.getInstanceAsync(user_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@BottomDialog.userRealm = realm
            }
        })

        btnSubmit = view.findViewById(R.id.btnSubmitRecipe)

        // adding on click listener for our button.
        btnSubmit.setOnClickListener {
            val recipe = Recipe(txtRec_Name.text.toString(),
                txtRec_Descr.text.toString(),
                txtRec_Ingr.text.toString(),
                txtRec_Steps.text.toString(),
                txtRec_Time.text.toString())
            recipeRealm.executeTransactionAsync { realm -> realm.insert(recipe) }


            userRealm.executeTransactionAsync { transactionRealm: Realm ->
                // get a frog from the database to update
                val userData = transactionRealm.where(User::class.java).findFirst()
                userData?.recipes?.add(recipe)
            }

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