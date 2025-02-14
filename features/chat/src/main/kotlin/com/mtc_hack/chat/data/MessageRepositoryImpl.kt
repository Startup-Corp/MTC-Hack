package com.mtc_hack.chat.data

class MessageRepositoryImpl {

    private val client = RetrofitClient.create()

    suspend fun sendMessage(message: ChatMessage): ChatMessage {
        val stringMessage = message.toString()
        return client.sendMessage(stringMessage)
    }
}