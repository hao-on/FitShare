package com.example.a491project

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.a491project.model.Post
import com.squareup.picasso.Picasso

class PostsAdapter(val posts: ArrayList<Post>, val context: Context) : RecyclerView.Adapter<PostsAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostsAdapter.ViewHolder, position: Int) {
        holder.username.text = posts[position].username
        holder.text.text = posts[position].text
        Picasso.get().load(posts[position].photo).into(holder.photo)
    }

    override fun getItemCount() = posts.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username)
        val text: TextView = itemView.findViewById(R.id.text)
        val photo: ImageView = itemView.findViewById(R.id.photo)
    }
}