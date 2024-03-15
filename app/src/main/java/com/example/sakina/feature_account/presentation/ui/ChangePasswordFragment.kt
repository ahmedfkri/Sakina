package com.example.sakina.feature_account.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sakina.MainActivity
import com.example.sakina.R
import com.example.sakina.core.util.Constant
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentChangePasswordBinding
import com.example.sakina.feature_account.domain.model.ChangePasswordRequest
import com.example.sakina.feature_account.presentation.view_model.AccountViewModel
import kotlinx.coroutines.launch


class ChangePasswordFragment : Fragment() {
    lateinit var binding: FragmentChangePasswordBinding
    lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).accountViewModel

        binding.confirmBtn.setOnClickListener {
          
            val oldPassword = binding.oldPassTxt.text.toString()
            val newPassword = binding.newPassTxt.text.toString()
            val changePasswordRequest = ChangePasswordRequest(oldPassword, newPassword)
            changePassword(changePasswordRequest)

        }
    }

    private fun changePassword(request: ChangePasswordRequest) {
        lifecycleScope.launch {
            viewModel.changePassword(request)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            Log.d(TAG, "changePassword: "+ resource.message)

                        }
                        is Resource.Error -> {
                            Log.d(TAG, "changePassword: "+ resource.message)
                        }

                        else -> {

                        }
                    }

                }
        }
    }

}


