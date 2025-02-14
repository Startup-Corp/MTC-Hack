package com.mtcapp.chat.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mtcapp.chat.data.ChatMessage
import com.mtc_hack.chat.R

class ChatRecyclerAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatRecyclerAdapter.ChatViewHolder>() {

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val senderTextView: TextView = view.findViewById(R.id.textViewSender)
        val messageTextView: TextView = view.findViewById(R.id.textViewMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_layout, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatMessage = messages[position]
        holder.senderTextView.text = chatMessage.senderId
        holder.messageTextView.text = chatMessage.message
    }

    override fun getItemCount() = messages.size
}