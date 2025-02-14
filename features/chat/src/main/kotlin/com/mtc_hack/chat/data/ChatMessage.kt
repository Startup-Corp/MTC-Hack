package com.mtc_hack.chat.data

data class ChatMessage(
    val id: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val senderId: String = ""
)
