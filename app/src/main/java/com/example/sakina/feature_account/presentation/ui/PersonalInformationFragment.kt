package com.example.sakina.feature_account.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.sakina.MainActivity
import com.example.sakina.core.util.Constant
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentPersonalInformationBinding
import com.example.sakina.feature_account.domain.model.PersonalInfoRequest
import com.example.sakina.feature_account.presentation.view_model.AccountViewModel
import kotlinx.coroutines.launch



class PersonalInformationFragment : Fragment() {
    lateinit var binding:FragmentPersonalInformationBinding
    lateinit var viewModel: AccountViewModel
    lateinit var request: PersonalInfoRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentPersonalInformationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).accountViewModel

        binding.saveBtn.setOnClickListener {
            val age=binding.ageEt.text.toString().toInt()
            val height=binding.heightEt.text.toString().toInt()
            val weight=binding.weightEt.text.toString().toInt()
            val personalInfo=PersonalInfoRequest(age,height,weight,
                diabetic = false,
                hypertension = false,
                hypotension = false,
                smoker = false
            )
            personalInformation(personalInfo)
            }

    }
    private fun personalInformation(request: PersonalInfoRequest) {
        lifecycleScope.launch {
            viewModel.personalInfo(request)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            Log.d(Constant.TAG, "data is saved: success " + resource.data)

                        }

                        is Resource.Error -> {
                            Log.d(Constant.TAG, "data is saved: ERROR " + resource.message)

                        }

                        else -> {
                            Log.d(Constant.TAG, "data is saved: else branch")
                        }
                    }
                }
        }
    }

}