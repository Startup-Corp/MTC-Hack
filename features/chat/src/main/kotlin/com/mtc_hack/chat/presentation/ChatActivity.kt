package com.mtc_hack.chat.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mtc_hack.chat.data.ChatMessage
import com.mtc_hack.chat.data.MessageRepositoryImpl
import com.mtc_hack.chat.databinding.ActivityChatBinding
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatRecyclerAdapter
    private val messages = mutableListOf<ChatMessage>()
    private lateinit var userName: String
    private val messageRepositoryImpl = MessageRepositoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView

        userName = intent.getStringExtra("USER_NAME") ?: "Anonymous"
        chatAdapter = ChatRecyclerAdapter(messages)
        recyclerView.adapter = chatAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonSendSetOnClickListener()
    }

    private fun buttonSendSetOnClickListener() {
        binding.buttonSend.setOnClickListener {
            lifecycleScope.launch {
                val messageText = binding.editTextMessage.text.toString()
                if (messageText.isNotEmpty()) {
                    val message = ChatMessage(
                        id = 0.toString(), message = messageText, senderId = userName
                    )
                    messages.add(message)
                    val responseMessage = messageRepositoryImpl.sendMessage(message)
                    messages.add(responseMessage)
                    binding.editTextMessage.text.clear()
                    chatAdapter.notifyDataSetChanged()
                    recyclerView.scrollToPosition(messages.size - 1)
                }
            }
        }
    }
}
