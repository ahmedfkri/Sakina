package com.example.sakina.feature_chat.domain.use_case

import android.util.Log
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_chat.domain.model.ChatMessage
import com.example.sakina.feature_chat.domain.model.MessageRequest
import com.example.sakina.feature_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class SendMessageUseCase(val repository: ChatRepository) {

    operator fun invoke(message: MessageRequest): Flow<Resource<ChatMessage>> = flow {

        try {
            emit(Resource.Loading())
            val response = repository.sendMessage(message)
            Log.d(TAG, "invoke: $response")
            emit(Resource.Success(ChatMessage(response.response, true, System.currentTimeMillis())))

        } catch (e: HttpException) {
            emit(Resource.Error("failed: ${e}", code = e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("failed: ${e}", code = 1))
        } catch (e: Exception) {
            emit(Resource.Error("failed: ${e}", code = 2))
        }
    }
}