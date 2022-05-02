package com.example.fitshare.Recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.Nullable
import com.example.fitshare.MainActivity
import com.example.fitshare.R
import com.example.fitshare.fitApp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.layout_add_recipe.*
import org.bson.types.ObjectId

class EditBottomDialog : BottomSheetDialogFragment() {
    private lateinit var recipeRealm: Realm
    private lateinit var userRealm: Realm
    private var user: io.realm.mongodb.User? = null
    private lateinit var btnSubmit: Button
    private lateinit var recipeName: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var prep: TextInputEditText
    private lateinit var ingredients: TextInputEditText
    private lateinit var steps: TextInputEditText
    private lateinit var partition: String
    private lateinit var recipeID2: String

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
                var recipeID2 = arguments?.getString("recipeID2")
                val myRecipe = recipeRealm.where(Recipe::class.java).equalTo("_id", ObjectId(recipeID2)).findFirst()

                recipeName.setText(myRecipe?.recipeName)
                description.setText(myRecipe?.description)
                prep.setText(myRecipe?.prepTime)
                ingredients.setText(myRecipe?.ingredients)
                steps.setText(myRecipe?.steps)
            }
        })


        recipeName =  view.findViewById(R.id.txtRec_Name)
        description = view.findViewById(R.id.txtRec_Descr)
        prep = view.findViewById(R.id.txtRec_Time)
        ingredients = view.findViewById(R.id.txtRec_Ingr)
        steps = view.findViewById(R.id.txtRec_Steps)
        btnSubmit = view.findViewById(R.id.btnSubmitRecipe)

        // adding on click listener for our button.
        btnSubmit.setOnClickListener {

            recipeRealm.executeTransactionAsync { transactionRealm: Realm ->
                var recipeID2 = arguments?.getString("recipeID2")
                val myRecipe = transactionRealm.where(Recipe::class.java).equalTo("_id", ObjectId(recipeID2)).findFirst()

                myRecipe?.recipeName = txtRec_Name.text.toString()
                myRecipe?.description = txtRec_Descr.text.toString()
                myRecipe?.ingredients = txtRec_Ingr.text.toString()
                myRecipe?.steps = txtRec_Steps.text.toString()
                myRecipe?.prepTime = txtRec_Time.text.toString()

                transactionRealm.insertOrUpdate(myRecipe)
            }
            dialog?.dismiss()
        }

        return view
    }

    companion object {
        fun newInstance(): EditBottomDialog {
            return EditBottomDialog()
        }
    }
}