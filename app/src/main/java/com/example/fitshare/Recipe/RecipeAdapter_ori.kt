package com.example.fitshare.Recipe

import android.app.AlertDialog
import android.content.Context
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.R
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import io.realm.kotlin.where
import io.realm.mongodb.sync.SyncConfiguration
import org.bson.types.ObjectId

/*
* TaskAdapter: extends the Realm-provided RealmRecyclerViewAdapter to provide data for a RecyclerView to display
* Realm objects on screen to a user.
*/
internal class RecipeAdapter_ori(data: OrderedRealmCollection<Recipe>, val user: io.realm.mongodb.User, private val partition: String) : RealmRecyclerViewAdapter<Recipe, RecipeAdapter_ori.RecipeViewHolder?>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.recipe_view, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val obj: Recipe? = getItem(position)
        holder.data = obj
        holder.recipeName.text = obj?.recipeName
        holder.description.text = "Description: " + obj?.description
        holder.ingredients.text = "Ingredients: " + obj?.ingredients
        holder.steps.text = "Steps: " + obj?.steps


        // multiselect popup to control status
        holder.itemView.setOnClickListener {
            run {
                val popup = PopupMenu(holder.itemView.context, holder.menu)
                val menu = popup.menu

                // add a delete button to the menu, identified by the delete code
                val deleteCode = -1
                menu.add(0, deleteCode, Menu.NONE, "Delete Recipe")

                val updateCode = 1
                menu.add(0, updateCode, Menu.NONE,  "Modify Recipe")

                // handle clicks for each button based on the code the button passes the listener
                popup.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item!!.itemId) {
                        deleteCode -> {
                            removeAt(holder.data?.id!!)
                        }
                        updateCode -> {
                            updateAt(holder.itemView.context, holder.data?.id!!)
                        }
                    }

                    true
                }
                popup.show()
            }
        }
    }

    private fun updateAt(holder: Context, id: ObjectId){
        val config = SyncConfiguration.Builder(user,partition).build()
        val realm: Realm =  Realm.getInstance(config)
        val thisRecipe = realm.where<Recipe>().equalTo("id", id).findFirst()

        val dialogBuilder = AlertDialog.Builder(holder)
        val scrollView = ScrollView(holder)
        val layout = LinearLayout(holder)
        layout.setOrientation(LinearLayout.VERTICAL)
        scrollView.addView(layout)

        val nameInput = EditText(holder)
        nameInput.setHint("Name")
        nameInput.setText(thisRecipe!!.recipeName)
        layout.addView(nameInput)

        val descInput = EditText(holder)
        descInput.setHint("Description")
        descInput.setText(thisRecipe!!.description)
        layout.addView(descInput)

        val ingrInput = EditText(holder)
        ingrInput.setHint("Ingredients")
        ingrInput.setText(thisRecipe!!.ingredients)
        layout.addView(ingrInput)

        val stepInput = EditText(holder)
        stepInput.setHint("Steps")
        stepInput.setText(thisRecipe!!.steps)
        layout.addView(stepInput)

        dialogBuilder.setCancelable(true).setPositiveButton("Submit") {dialog, _ -> run{
            dialog.dismiss()

            realm.executeTransactionAsync{
                val oldRecipe = it.where<Recipe>().equalTo("id", id).findFirst()
                oldRecipe!!.recipeName = nameInput.text.toString()
                oldRecipe!!.description = descInput.text.toString()
                oldRecipe!!.ingredients = ingrInput.text.toString()
                oldRecipe!!.steps = stepInput.text.toString()
            }

        }
        }.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel()}

        val dialog = dialogBuilder.create()
        dialog.setView(scrollView)
        dialog.setTitle("Modifying Recipe..." + "\n" + "Enter Recipe Information")
        dialog.show()
        dialog.getWindow()?.setLayout(850, 965)
        dialog.getWindow()?.exitTransition
    }

    private fun removeAt(id: ObjectId) {
        // need to create a separate instance of realm to issue an update, since this event is
        // handled by a background thread and realm instances cannot be shared across threads
        val config = SyncConfiguration.Builder(user, partition)
            .build()

        // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
        val realm: Realm = Realm.getInstance(config)
        // execute Transaction asynchronously to avoid blocking the UI thread
        realm.executeTransactionAsync {
            // using our thread-local new realm instance, query for and delete the task
            val item = it.where<Recipe>().equalTo("id", id).findFirst()
            item?.deleteFromRealm()
        }
        // always close realms when you are done with them!
        realm.close()
    }

    internal inner class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var recipeName: TextView = view.findViewById(R.id.name)
        var description: TextView = view.findViewById(R.id.description)
        var ingredients: TextView = view.findViewById(R.id.ingredients)
        var steps: TextView = view.findViewById(R.id.steps)
        var data: Recipe? = null
        var menu: TextView = view.findViewById(R.id.menu)
    }
}
