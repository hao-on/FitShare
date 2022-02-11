package com.mongodb.tasktracker.model

import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mongodb.tasktracker.R
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
internal class RecipeAdapter(data: OrderedRealmCollection<Recipe>, val user: io.realm.mongodb.User, private val partition: String) : RealmRecyclerViewAdapter<Recipe, RecipeAdapter.TaskViewHolder?>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.task_view, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val obj: Recipe? = getItem(position)
        holder.data = obj
        holder.recipeName.text = obj?.recipeName
        holder.description.text = obj?.description
        holder.ingredients.text = obj?.ingredients
        holder.steps.text = obj?.steps

        // multiselect popup to control status
        holder.itemView.setOnClickListener {
            run {
                val popup = PopupMenu(holder.itemView.context, holder.menu)
                val menu = popup.menu

                // the menu should only contain statuses different from the current status

                // add a delete button to the menu, identified by the delete code
                val deleteCode = -1
                menu.add(0, deleteCode, Menu.NONE, "Delete Task")

                // handle clicks for each button based on the code the button passes the listener
                popup.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item!!.itemId) {
                        deleteCode -> {
                            removeAt(holder.data?.id!!)
                        }
                    }

                    true
                }
                popup.show()
            }
        }
    }

    private fun changeStatus(status: TaskStatus, id: ObjectId) {
        // need to create a separate instance of realm to issue an update
        // since realm instances cannot be shared across threads
        val config = SyncConfiguration.Builder(user, partition)
            .build()

        // Sync all realm changes via a new instance, and when that instance has been successfully
        // created connect it to an on-screen list (a recycler view)
        val realm: Realm = Realm.getInstance(config)
        // execute Transaction asynchronously to avoid blocking the UI thread
        realm.executeTransactionAsync {
            // using our thread-local new realm instance, query for and update the task status
            val item = it.where<Task>().equalTo("id", id).findFirst()
            item?.statusEnum = status
        }
        // always close realms when you are done with them!
        realm.close()
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

    internal inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var recipeName: TextView = view.findViewById(R.id.name)
        var description: TextView = view.findViewById(R.id.description)
        var ingredients: TextView = view.findViewById(R.id.ingredients)
        var steps: TextView = view.findViewById(R.id.steps)
        var data: Recipe? = null
        var menu: TextView = view.findViewById(R.id.menu)

    }
}
