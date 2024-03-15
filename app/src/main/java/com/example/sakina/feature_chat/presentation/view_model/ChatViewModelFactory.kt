package com.example.sakina.feature_chat.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sakina.feature_advice.presentation.view_model.AdviceViewModel
import com.example.sakina.feature_chat.domain.use_case.SendMessageUseCase

class ChatViewModelFactory(private val sendMessageUseCase: SendMessageUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(
            sendMessageUseCase
        ) as T
    }
}
