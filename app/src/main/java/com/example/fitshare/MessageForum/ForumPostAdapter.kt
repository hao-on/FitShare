package com.example.fitshare.MessageForum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.R
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class ForumPostAdapter (data: OrderedRealmCollection<ForumPost>,
                        val user: io.realm.mongodb.User,
                        private val partition: String)
                        : RealmRecyclerViewAdapter<ForumPost,
                        ForumPostAdapter.ForumViewHolder?>(data, true){

    private lateinit var myListener: onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ForumViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.layout_forum_post, parent, false)
        return ForumViewHolder(itemView, myListener)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        val obj: ForumPost? = getItem(position)
        holder.data = obj
        holder.title.text = obj?.title
        holder.creator.text = obj?.creator
        holder.time.text = obj?.dateCreated

    }

    inner class ForumViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.fPostTitle)
        var creator: TextView = view.findViewById(R.id.fPostCreator)
        var time: TextView = view.findViewById(R.id.dateCreated)
        var data: ForumPost? = null

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }


    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        myListener = listener
    }
}