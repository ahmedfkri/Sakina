package com.example.sakina.feature_account.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sakina.MainActivity
import com.example.sakina.R
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentMedicalInfoBinding
import com.example.sakina.feature_account.domain.model.PersonalInfoRequest
import com.example.sakina.feature_account.presentation.view_model.AccountViewModel
import kotlinx.coroutines.launch


class MedicalInfoFragment : Fragment() {

    lateinit var binding: FragmentMedicalInfoBinding

    lateinit var request: PersonalInfoRequest

    lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = (activity as MainActivity).accountViewModel

        getData()

        binding = FragmentMedicalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        request = PersonalInfoRequest(
            null, null, null,
            diabetic = false,
            hypertension = false,
            hypotension = false,
            smoker = false
        )



        binding.btnBack.setOnClickListener {

            lifecycleScope.launch {
                viewModel.personalInfo(request).collect { resourse ->
                    when (resourse) {
                        is Resource.Success -> {
                            Log.d(TAG, "info: done")
                            findNavController().navigateUp()
                        }

                        is Resource.Error -> {
                            Log.d(TAG, "info:" + resourse.message.toString())
                            findNavController().navigateUp()
                        }

                        else -> Log.d(TAG, "info: loading")
                    }
                }
            }

        }



        binding.toggleHypertension.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.hypertensionFalse -> {

                        request.hypertension = false

                    }

                    R.id.hypertensionTrue -> {

                        request.hypertension = true

                    }
                }
            }
        }

        binding.toggleHypotension.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.hypotensionFalse -> {

                        request.hypotension = false

                    }

                    R.id.hypotensionTrue -> {

                        request.hypotension = true

                    }
                }
            }
        }

        binding.toggleDiabetes.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.diabetesFalse -> {

                        request.diabetic = false

                    }

                    R.id.diabetesTrue -> {

                        request.diabetic = true

                    }
                }
            }
        }

        binding.toggleSmoker.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    binding.smokerFalse.id -> {

                        request.smoker = false

                    }

                    binding.smokerTrue.id -> {

                        request.smoker = true

                    }
                }
            }
        }


    }

    private fun getData() {
        lifecycleScope.launch {
            viewModel.getInformation().collect { resourse ->
                when (resourse) {
                    is Resource.Success -> {
                        Log.d(TAG, "get: done")
                        request = resourse.data!!
                        showData()
                    }

                    is Resource.Error -> Log.d(TAG, "get:" + resourse.message.toString())
                    else -> Log.d(TAG, "get: loading")
                }
            }
        }
    }

    private fun showData() {

        binding.apply {
            if (request.diabetic!!) {
                toggleDiabetes.check(diabetesTrue.id)
            } else {
                toggleDiabetes.check(diabetesFalse.id)
            }
            if (request.hypertension!!) {
                toggleHypertension.check(hypertensionTrue.id)
            } else {
                toggleHypertension.check(hypertensionFalse.id)
            }
            if (request.hypotension!!) {
                toggleHypotension.check(hypotensionTrue.id)
            } else {
                toggleHypotension.check(hypotensionFalse.id)
            }
            if (request.smoker!!) {
                toggleSmoker.check(smokerTrue.id)
            } else {
                toggleSmoker.check(smokerFalse.id)
            }
        }


    }

}