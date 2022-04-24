package com.example.fitshare.Messaging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitshare.R
import com.example.fitshare.Recipe.Recipe
import io.realm.OrderedRealmCollection

class MessagesAdapter(data: OrderedRealmCollection<Message>,
                      val user: io.realm.mongodb.User,
                      private val partition: String
) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>(){
    companion object {
        private const val SENT = 0
        private const val RECEIVED = 1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = when (viewType) {
            SENT -> {
                LayoutInflater.from(parent.context).inflate(R.layout.item_sent, parent, false)
            }
            else -> {
                LayoutInflater.from(parent.context).inflate(R.layout.item_received, parent, false)
            }
        }
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int = ChatManager.msgEntries!!.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(ChatManager.msgEntries?.get(position)!!)
    }

    override fun getItemViewType(position: Int): Int {
        return if (ChatManager.msgEntries?.get(position)!!.username == ChatManager.username) {
            SENT
        } else {
            RECEIVED
        }
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val messageText: TextView? = itemView.findViewById(R.id.message_text)
        private val messageName: TextView? = itemView.findViewById(R.id.text_name)
        fun bind(message: Message) {
            messageText?.text = message.message
            if (messageName != null){
                messageName.text = message.username
            }
        }
    }
}