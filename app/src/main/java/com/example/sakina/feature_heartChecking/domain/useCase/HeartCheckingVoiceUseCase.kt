package com.example.sakina.feature_heartChecking.domain.useCase

import android.util.Log
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_heartChecking.domain.model.HeartResponse
import com.example.sakina.feature_heartChecking.domain.repository.HeartRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class HeartCheckingVoiceUseCase(private val heartRepo: HeartRepo) {
    operator fun invoke(file: File): Flow<Resource<HeartResponse>> = flow {
        try {
            emit(Resource.Loading())
            val voice = addHeartVoice(file)
            val response = heartRepo.heartChecking(voice)
            Log.d("heartCheckingVoiceUseCase", "invoke: " + response.label)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error("failed: $e", code = e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("failed: $e", code = 1))
        } catch (e: Exception) {
            emit(Resource.Error("failed: $e", code = 2))
        }
    }
}

fun addHeartVoice(file: File): MultipartBody.Part {
    val voiceDetails: RequestBody = RequestBody.create(
        "audio/wav".toMediaTypeOrNull(),
        file
    )
    return MultipartBody.Part.createFormData(
        "voice",
        file.name, voiceDetails
    )
}
