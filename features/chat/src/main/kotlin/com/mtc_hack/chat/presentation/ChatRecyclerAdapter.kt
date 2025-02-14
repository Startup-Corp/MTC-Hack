package com.mtc_hack.chat.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mtc_hack.chat.databinding.ChatLayoutBinding
import com.mtc_hack.chat.data.ChatMessage

class ChatRecyclerAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<ChatRecyclerAdapter.ChatViewHolder>() {

    class ChatViewHolder(val binding: ChatLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ChatLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatMessage = messages[position]
        holder.binding.textViewSender.text = chatMessage.senderId
        holder.binding.textViewMessage.text = chatMessage.message
    }

    override fun getItemCount() = messages.size
}