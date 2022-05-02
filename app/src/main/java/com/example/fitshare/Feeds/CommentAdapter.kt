package com.example.fitshare.Feeds

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.R
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import xyz.hanks.library.bang.SmallBangView

class CommentAdapter(data: OrderedRealmCollection<Comment>,
                  val user: io.realm.mongodb.User
) : RealmRecyclerViewAdapter<Comment, CommentAdapter.CommentViewHolder?>
    (data, true) {

//    private lateinit var mListener: CommentAdapter.onClickListener
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CommentViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.layout_comment, parent, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val obj: Comment? = getItem(position)
        holder.data = obj
        holder.time.text = obj?.date.toString()
        holder.content.text = obj?.comment.toString()
        holder.username.text =  obj?.username.toString()
    }

    inner class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var content: TextView = view.findViewById(R.id.tvComment)
        var username: TextView = view.findViewById(R.id.tvUsername)
        var time: TextView = view.findViewById(R.id.tvTime)
        var data: Comment? = null
    }

//
//    interface onClickListener{
//        fun setComment(content: TextView, position: Int)
//    }
//
//    fun setOnClickListener(listener: onClickListener){
//        mListener = listener
//    }

}