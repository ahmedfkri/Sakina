package com.example.sakina.feature_skin_checking.presentation.view_model

import androidx.lifecycle.ViewModel
import com.example.sakina.core.util.Resource
import com.example.sakina.feature_skin_checking.domain.model.SkinResponse
import com.example.sakina.feature_skin_checking.domain.use_case.UploadImageUseCase
import kotlinx.coroutines.flow.Flow
import java.io.File

class SkinCheckingViewModel(val uploadImageUseCase: UploadImageUseCase): ViewModel() {

    fun uploadSkinImage(file: File): Flow<Resource<SkinResponse>>{
        return  uploadImageUseCase(file)
    }

}