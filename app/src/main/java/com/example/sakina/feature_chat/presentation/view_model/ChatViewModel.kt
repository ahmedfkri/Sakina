package com.example.sakina.feature_chat.presentation.view_model

import android.app.Notification.MessagingStyle.Message
import androidx.lifecycle.ViewModel
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_chat.domain.model.ChatMessage
import com.example.sakina.feature_chat.domain.model.MessageRequest
import com.example.sakina.feature_chat.domain.use_case.SendMessageUseCase
import kotlinx.coroutines.flow.Flow

class ChatViewModel(
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    fun sendMessage(message: MessageRequest): Flow<Resource<ChatMessage>> {
        return sendMessageUseCase(message)
    }

}