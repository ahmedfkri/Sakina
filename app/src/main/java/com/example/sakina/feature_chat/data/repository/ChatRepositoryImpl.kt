package com.example.sakina.feature_chat.data.repository

import com.example.sakina.core.data.remote.models_api.ModelsRetrofitClient
import com.example.sakina.feature_chat.domain.model.MessageRequest
import com.example.sakina.feature_chat.domain.model.MessageResponse
import com.example.sakina.feature_chat.domain.repository.ChatRepository

class ChatRepositoryImpl : ChatRepository {

    override suspend fun sendMessage(message: MessageRequest): MessageResponse {
        return ModelsRetrofitClient.api.sendMessage(message)
    }
}