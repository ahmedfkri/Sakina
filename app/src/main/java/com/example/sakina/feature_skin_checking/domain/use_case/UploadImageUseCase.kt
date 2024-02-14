package com.example.sakina.feature_skin_checking.domain.use_case

import android.util.Log
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_skin_checking.domain.model.SkinResponse
import com.example.sakina.feature_skin_checking.domain.repository.SkinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class UploadImageUseCase(val repository: SkinRepository) {

    operator fun invoke(file: File): Flow<Resource<SkinResponse>> = flow {
        try {
            emit(Resource.Loading())
            val image = getImageReadyForUpload(file)
            val response = repository.checkSkin(image)
            Log.d("UploadImageUseCase", "invoke: " + response.description)
            emit(Resource.Success(response))

        } catch (e: HttpException) {
            emit(Resource.Error("failed: ${e}", code = e.code()))
        } catch (e: IOException) {
            emit(Resource.Error("failed: ${e}", code = 1))
        } catch (e: Exception) {
            emit(Resource.Error("failed: ${e}", code = 2))
        }

    }

    fun getImageReadyForUpload(file: File): MultipartBody.Part {

        val image: RequestBody = RequestBody.create(
            "image/*".toMediaTypeOrNull(),
            file
        )

        return MultipartBody.Part.createFormData(
            "image",
            file.name, image
        )

    }
}