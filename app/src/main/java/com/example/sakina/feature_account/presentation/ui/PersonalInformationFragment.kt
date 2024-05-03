package com.example.sakina.feature_account.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sakina.MainActivity
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentPersonalInformationBinding
import com.example.sakina.feature_account.domain.model.PersonalInfoRequest
import com.example.sakina.feature_account.presentation.view_model.AccountViewModel
import kotlinx.coroutines.launch


class PersonalInformationFragment : Fragment() {
    lateinit var binding: FragmentPersonalInformationBinding
    lateinit var viewModel: AccountViewModel
    lateinit var request: PersonalInfoRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).accountViewModel

        showData()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveBtn.setOnClickListener {
            updateInformation()
            findNavController().navigateUp()
        }

    }

    private fun updateInformation() {
        if (isValidData()) {
            val age = binding.edtAge.text.toString().toInt()
            val height = binding.edtHeight.text.toString().toInt()
            val weight = binding.edtWeight.text.toString().toInt()

            val request = PersonalInfoRequest(
                age = age,
                hight = height,
                wight = weight,
                diabetic = null,
                hypertension = null,
                hypotension = null,
                smoker = null
            )
            makeRequest(request)

        } else {
            Toast.makeText(requireContext(), "Please enter valid data", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun isValidData(): Boolean {
        return !(binding.edtAge.text.isNullOrBlank() || binding.edtHeight.text.isNullOrBlank() || binding.edtWeight.text.isNullOrBlank())

    }

    private fun makeRequest(request: PersonalInfoRequest) {
        lifecycleScope.launch {
            viewModel.personalInfo(request).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d(Constant.TAG, "personal info : success " + resource.data)
                    }

                    is Resource.Error -> {
                        Log.d(Constant.TAG, "personal info: ERROR " + resource.message)

                    }

                    else -> {
                        Log.d(Constant.TAG, "personal info: else branch")
                    }
                }
            }
        }
    }

    private fun showData() {

        lifecycleScope.launch {
            viewModel.getInformation().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val data = resource.data
                        binding.apply {
                            edtAge.setText(data!!.age.toString())
                            edtHeight.setText(data.hight.toString())
                            edtWeight.setText(data.wight.toString())
                        }
                    }

                    is Resource.Error -> {
                        Log.d(Constant.TAG, "personal info: ERROR " + resource.message)

                    }

                    else -> {
                        Log.d(Constant.TAG, "personal info: else branch")
                    }
                }
            }
        }

        /*   binding.apply {
               edtAge.setText(MySharedPref.getInt(AGE, 0).toString())
               edtWeight.setText(MySharedPref.getInt(WEIGHT, 0).toString())
               edtHeight.setText(MySharedPref.getInt(HEIGHT, 0).toString())
           }*/

    }

}