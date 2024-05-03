package com.example.sakina.feature_heartChecking.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sakina.R
import com.example.sakina.databinding.FragmentHeartCheckingBinding
import com.example.sakina.feature_heartChecking.data.repository.HeartCheckingRepo
import com.example.sakina.feature_heartChecking.domain.model.HeartResponse
import com.example.sakina.feature_heartChecking.domain.useCase.HeartCheckingVoiceUseCase
import com.example.sakina.feature_heartChecking.presentation.viewModel.HeartViewModel
import com.example.sakina.feature_heartChecking.presentation.viewModel.HeartViewModelFactory
import java.io.File

class HeartCheckingFragment : Fragment() {
    private lateinit var binding: FragmentHeartCheckingBinding
    private lateinit var soundFile: File
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

        binding.loAddSound.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "audio/*"
            }
            pickSoundFileContract.launch(intent)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_heartCheckingFragment_to_homeFragment)
        }

        var toggle = false

        binding.loResponse.setOnClickListener {
            if (toggle) {
                binding.loResponse.elevation = 10F
                toggle = !toggle
            } else {
                binding.loResponse.elevation = 0F
                toggle = !toggle
            }

        }
    }

    private val pickSoundFileContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    soundFile = uri.toFile(requireContext())!!
                    if (this::soundFile.isInitialized) {
                        check()
                    } else {
                        Toast.makeText(requireContext(), "Please Add File ", Toast.LENGTH_LONG)
                            .show()
                    }
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

        activateResponse(
            HeartResponse(
                "label",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        " Lorem Ipsum has been the industry's standard \ndummy text ever since the 1500s," +
                        "\n when an unknown printer took a galley of type and scrambled it to make a type\n" +
                        " specimen book. It has survived not only five centuries,\n" +
                        " but also the leap into electronic typesetting,\n" +
                        " remaining essentially unchanged. It was popularised in the 1960s\n" +
                        " with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                "OK?"
            )
        )

        /*        lifecycleScope.launch {
                    viewModel.heartVoice(voiceFile).collect { resource ->
                        when (resource) {
                            is Resource.Success -> {
                                Log.d(TAG, "Check:" + resource.data!!.description)
                                activateResponse(resource.data)
                            }

                            is Resource.Error -> {
                                binding.progressBar.isVisible = false
                                Log.d(TAG, "error: " + resource.message)
                            }

                            else -> {
                                binding.progressBar.isVisible = true

                            }
                        }
                    }

                }*/
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
            txtIntroduction.isVisible = false
            progressBar.isVisible = false
            loResponse.isVisible = true
            imgHeart.isVisible = false
            imgSakina.isVisible = true
            txtResponseLabel.text = response.label
            txtResponseDesc.text = response.description
            txtResponseAdvice.text = response.advice
            txtAddSound.text = soundFile.path
            txtAddSound.textSize = 8f

        }
    }


}
