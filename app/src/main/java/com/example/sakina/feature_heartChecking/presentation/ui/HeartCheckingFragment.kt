package com.example.sakina.feature_heartChecking.presentation.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentHeartCheckingBinding
import com.example.sakina.feature_heartChecking.data.repository.HeartCheckingRepo
import com.example.sakina.feature_heartChecking.domain.model.HeartResponse
import com.example.sakina.feature_heartChecking.domain.useCase.HeartCheckingVoiceUseCase
import com.example.sakina.feature_heartChecking.presentation.viewModel.HeartViewModel
import com.example.sakina.feature_heartChecking.presentation.viewModel.HeartViewModelFactory
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class HeartCheckingFragment : Fragment() {
    private lateinit var binding: FragmentHeartCheckingBinding
    private lateinit var voiceFile: File
    private lateinit var viewModel: HeartViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeartCheckingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = HeartCheckingRepo()
        val heartVoiceUseCase = HeartCheckingVoiceUseCase(repo)
        viewModel = ViewModelProvider(
            requireActivity(), HeartViewModelFactory(heartVoiceUseCase)
        )[HeartViewModel::class.java]

        binding.btnAddVoice.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "audio/*"
            }
            pickSoundFileContract.launch(intent)
        }
        binding.btnDiagnoseHeart.setOnClickListener {
            if (this::voiceFile.isInitialized) {
                check()
            } else {
                Toast.makeText(requireContext(), "Please Add File ", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private val pickSoundFileContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    voiceFile = uri.toFile(requireContext())!!
                }
            }
        }


    /*    private fun uploadVoiceFile(file: File) {
            val client = OkHttpClient.Builder()
                .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                .build()

            val requestBuilder = Request.Builder()
                .url(this)

            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "voiceFile",
                    file.name,
                    file.asRequestBody("voice/*".toMediaTypeOrNull())
                )
                .build()

            val request = requestBuilder
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                println("File uploaded successfully.")
            } else {
                println("File upload failed. Error: ${response.code}")
            }
        }
        */
     */

    private fun check() {
        lifecycleScope.launch {
            viewModel.heartVoice(voiceFile).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d(TAG, "Check:" + resource.data!!.description)
                        activateResponse(resource.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        activateResponse2()
                        Log.d(TAG, "error: " + resource.message)
                    }

                    else -> {
                        binding.progressBar.isVisible = true

                    }
                }
            }

        }
    }

    private fun Uri.toFile(context: Context): File? {
        val inputStream = context.contentResolver.openInputStream(this)
        val tempFile = File.createTempFile("temp", ".wav")
        return try {
            tempFile.outputStream().use { fileOut ->
                inputStream?.copyTo(fileOut)
            }
            tempFile.deleteOnExit()
            inputStream?.close()
            tempFile
        } catch (e: Exception) {
            null
        }
    }

    private fun activateResponse(response: HeartResponse) {
        binding.apply {
            txtDiagDesc.isVisible = false
            btnAddVoice.isVisible = false
            btnDiagnoseHeart.isVisible = false
            progressBar.isVisible = false
            responseLine.isVisible = true
            imgHeart.isVisible = false
            labelTv.text = response.label
            descTv.text = response.description
            adviceTv.text = response.advice
        }
    }

    private fun activateResponse2() {
        binding.apply {
            txtDiagDesc.isVisible = false
            btnDiagnoseHeart.isVisible = false
            btnAddVoice.isVisible = false
            progressBar.isVisible = false
            responseLine.isVisible = true
        }
    }
}