package com.example.sakina.feature_chat.domain.model

import java.sql.Timestamp

data class ChatMessage(
    val message: String,
    val isBotMessage: Boolean,
    val timestamp: Long
)
