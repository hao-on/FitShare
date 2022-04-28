package com.example.fitshare.MessageForum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.R
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class ForumCommentAdapter (data: OrderedRealmCollection<ForumComment>,
                           val user: io.realm.mongodb.User,
                           private val partition: String)
    : RealmRecyclerViewAdapter<ForumComment,
        ForumCommentAdapter.ForumCommentViewHolder?>(data, true){

    private lateinit var myListener: onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ForumCommentViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.layout_forum_comment, parent, false)
        return ForumCommentViewHolder(itemView, myListener)
    }

    override fun onBindViewHolder(holder: ForumCommentViewHolder, position: Int) {
        val obj: ForumComment? = getItem(position)
        holder.data = obj
        holder.message.text = obj?.message
        holder.creator.text = obj?.creator
        holder.time.text = obj?.dateCreated

    }

    inner class ForumCommentViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        //var title: TextView = view.findViewById(R.id.fPostTitle)
        //var content: TextView
        var message: TextView = view.findViewById(R.id.forumMessage)
        var creator: TextView = view.findViewById(R.id.commentCreator)
        var time: TextView = view.findViewById(R.id.dateCreated)
        var data: ForumComment? = null

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