package com.example.fitshare.Feeds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.R
import com.example.fitshare.Recipe.Recipe
import com.example.fitshare.Recipe.RecipeAdapter
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import xyz.hanks.library.bang.SmallBangView

class PostAdapter(data: OrderedRealmCollection<Post>,
                    val user: io.realm.mongodb.User,
                    private val partition: String
) : RealmRecyclerViewAdapter<Post, PostAdapter.PostViewHolder?>
    (data, true) {

    private lateinit var mListener: onClickListener
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): PostViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).
        inflate(R.layout.layout_post, parent, false)
        return PostViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val obj: Post? = getItem(position)
        holder.data = obj
        holder.postText.text = obj?.content.toString()
        holder.username.text = obj?.profile?.username.toString()
        holder.tvLike.text = obj?.likesList?.size.toString()
    }

    inner class PostViewHolder(view: View, listener: onClickListener) : RecyclerView.ViewHolder(view) {
        var postText: TextView = view.findViewById(R.id.tvPostText)
        var username: TextView = view.findViewById(R.id.tvUsername)
        var data: Post? = null
        var btnLike: SmallBangView = view.findViewById(R.id.btnLike)
        var btnComment: ImageButton = view.findViewById(R.id.btnComment)
        var tvLike: TextView = view.findViewById(R.id.tvLike)

        init{
            listener.isLikeButtonSelected(btnLike, adapterPosition)
            btnLike.setOnClickListener{
                listener.onLikeButtonClick(btnLike, adapterPosition)
            }
            btnComment.setOnClickListener{
                listener.onCommentButtonClick(btnComment, adapterPosition)
            }
        }
    }

    interface onClickListener{
        fun isLikeButtonSelected(button: SmallBangView, position: Int)
        fun onLikeButtonClick(button: SmallBangView, position: Int)
        fun onCommentButtonClick(button: ImageButton, position: Int)
    }

    fun setOnClickListener(listener: onClickListener){
        mListener = listener
    }

}