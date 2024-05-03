package com.example.sakina.feature_skin_checking.presentation.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentSkinBinding
import com.example.sakina.feature_skin_checking.data.repository.SkinRepositoryImpl
import com.example.sakina.feature_skin_checking.domain.model.SkinResponse
import com.example.sakina.feature_skin_checking.domain.use_case.UploadImageUseCase
import com.example.sakina.feature_skin_checking.presentation.view_model.SkinCheckingViewModel
import com.example.sakina.feature_skin_checking.presentation.view_model.SkinViewModelFactory
import java.io.File


class SkinFragment : Fragment() {

    private lateinit var binding: FragmentSkinBinding
    private lateinit var imageFile: File
    private lateinit var viewModel: SkinCheckingViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSkinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = SkinRepositoryImpl()
        val uploadImageUseCase = UploadImageUseCase(repository)

        viewModel = ViewModelProvider(
            requireActivity(), SkinViewModelFactory(uploadImageUseCase)
        )[SkinCheckingViewModel::class.java]

        binding.imgSkin.setOnClickListener {
            hidePreviousDiagnosis()
            openImageChooser()
        }

        binding.btnAddImage.setOnClickListener {
            hidePreviousDiagnosis()
            openImageChooser()
        }
    }

    private fun hidePreviousDiagnosis() {
        binding.apply {
            loText.isVisible = false
            btnAddImage.isVisible = true
        }
    }

    private fun diagnose() {

        showResponse(
            SkinResponse(
                "Advice",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                        " Lorem Ipsum has been the industry's standard \ndummy text ever since the 1500s," +
                        "\n when an unknown printer took a galley of type and scrambled it to make a type\n" +
                        " specimen book. It has survived not only five centuries,\n" +
                        " but also the leap into electronic typesetting,\n" +
                        " remaining essentially unchanged. It was popularised in the 1960s\n" +
                        " with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                "Label"
            )
        )


        /*lifecycleScope.launch {
            viewModel.uploadSkinImage(imageFile).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d(TAG, "diagnose: " + resource.data!!.description)
                        showResponse(resource.data)
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "error: " + resource.message)
                    }

                    else -> {
                        binding.progressBa.isVisible = true
                    }
                }
            }
        }*/
    }

    private fun showResponse(response: SkinResponse) {
        binding.apply {
            progressBa.isVisible = false
            txtIntroduction.isVisible = false
            btnAddImage.isVisible = false


            loText.isVisible = true
            txtLabel.text = response.label
            txtDescription.text = response.description
            txtAdvice.text = response.advice

            val params = binding.imgSkin.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = 32
            binding.imgSkin.layoutParams = params

        }
    }


    private fun openImageChooser() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                //requestCameraPermission()
            } else if (options[item] == "Choose from Gallery") {
                Toast.makeText(requireContext(), "open gall", Toast.LENGTH_SHORT).show()
                openGallery()
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                Glide.with(this).load(imgUri).apply(
                    RequestOptions.bitmapTransform(CircleCrop())
                ).into(binding.imgSkin)
                imageFile = imgUri?.toFile(requireContext())!!

                if (this::imageFile.isInitialized) {
                    diagnose()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please Choose Image First",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            }
        }


    private fun openGallery() {
        val pickImg =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        changeImage.launch(pickImg)
    }


    private fun Uri.toFile(context: Context): File? {
        val inputStream = context.contentResolver.openInputStream(this)
        val tempFile = File.createTempFile("temp", ".jpg")
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


}
