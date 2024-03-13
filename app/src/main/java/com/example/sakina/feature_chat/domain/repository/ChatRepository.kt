package com.example.sakina.feature_chat.domain.repository

import com.example.sakina.feature_chat.domain.model.MessageRequest
import com.example.sakina.feature_chat.domain.model.MessageResponse


interface ChatRepository {

    suspend fun sendMessage(message: MessageRequest): MessageResponse
}