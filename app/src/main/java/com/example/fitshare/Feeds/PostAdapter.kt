package com.example.fitshare.Feeds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.R
import com.example.fitshare.Recipe.Recipe
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class PostAdapter(data: OrderedRealmCollection<Post>,
                    val user: io.realm.mongodb.User,
                    private val partition: String
) : RealmRecyclerViewAdapter<Post, PostAdapter.PostViewHolder?>
    (data, true) {


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PostViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.layout_post, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val obj: Post? = getItem(position)
        holder.data = obj
        holder.postText.text = obj?.content.toString()
        holder.username.text = obj?.profile?.username.toString()
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var postText: TextView = view.findViewById(R.id.tvPostText)
        var username: TextView = view.findViewById(R.id.tvUsername)
        var data: Post? = null

    }

}