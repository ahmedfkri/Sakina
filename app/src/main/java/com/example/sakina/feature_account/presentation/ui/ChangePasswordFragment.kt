package com.example.sakina.feature_account.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sakina.MainActivity
import com.example.sakina.R
import com.example.sakina.core.data.MySharedPref
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

        showData()

        binding.confirmBtn.setOnClickListener {

            val oldPassword = binding.edtOldPass.text.toString()
            val newPassword = binding.edtNewPass.text.toString()
            val changePasswordRequest = ChangePasswordRequest(oldPassword, newPassword)
            changePassword(changePasswordRequest)
            findNavController().navigate(R.id.action_changePasswordFragment_to_accountFragment)
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_changePasswordFragment_to_accountFragment)
        }
    }

    private fun changePassword(request: ChangePasswordRequest) {
        lifecycleScope.launch {
            viewModel.changePassword(request)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            Log.d(TAG, "changePassword: " + resource.message)
                            Toast.makeText(
                                requireContext(),
                                "Password changed Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Resource.Error -> {
                            Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        else -> {
                            Log.d(TAG, "changePassword: else branch")
                        }
                    }

                }
        }
    }
    private fun showData() {
        binding.edtOldPass.setText(MySharedPref.getString(Constant.CURRENT_PASSWORD,""))
    }

}


