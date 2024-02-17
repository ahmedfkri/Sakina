package com.example.sakina.feature_heartChecking.presentation.ui

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = HeartCheckingRepo()
        val heartVoiceUseCase = HeartCheckingVoiceUseCase(repo)
        viewModel = ViewModelProvider(
            requireActivity(), HeartViewModelFactory(heartVoiceUseCase)
        )[HeartViewModel::class.java]

        binding.addVoice.setOnClickListener {
            val option = arrayOf<CharSequence>("Add", "Delete")
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Send as a file")
            builder.setItems(option) { dialog, item ->
                if (option[item] == "Add") {
                    "".uploadVoiceFile(voiceFile)
                } else {
                    dialog.dismiss()
                }
            }
        }
        binding.check.setOnClickListener {
            if (this::voiceFile.isInitialized) {
                check()
            } else {
                Toast.makeText(requireContext(), "Please Add File ", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun String.uploadVoiceFile(file: File) {
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

    private fun check() {
        lifecycleScope.launch {
            viewModel.heartVoice(voiceFile).collect{resource ->
                when(resource){
                    is Resource.Success ->{
                        Log.d(TAG,"Check:" + resource.data!!.label)
                        activateResponse(resource.data)
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "error: " + resource.message)
                    }

                    else -> {

                    }
                }
            }

        }
    }
    private fun activateResponse(response: HeartResponse) {
        binding.apply {
            responseLine.isVisible=true
            labelTv.text=response.label
            descTv.text=response.description
            adviceTv.text=response.advice
        }
    }
}