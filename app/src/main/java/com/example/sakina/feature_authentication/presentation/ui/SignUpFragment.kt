package com.example.sakina.feature_authentication.presentation.ui

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
import com.example.sakina.R
import com.example.sakina.core.util.Constant
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentSignUpBinding
import com.example.sakina.feature_authentication.domain.mode.AuthenticateRequest
import com.example.sakina.feature_authentication.domain.mode.RegisterRequest
import com.example.sakina.feature_authentication.presentation.view_model.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    lateinit var viewModel: AuthViewModel
    lateinit var registerRequest: RegisterRequest
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).authViewModel

        binding.btnSignUp.setOnClickListener {
            binding.apply {
                registerRequest = RegisterRequest(
                    firstName = edtFirstName.text.toString(),
                    lastName = edtLastName.text.toString(),
                    email = edtEmail.text.toString(),
                    password = edtPass.text.toString()
                )
            }
            validateRequest()
        }


        binding.txtLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

    }


    private fun checkEmailDuplication() {
        lifecycleScope.launch {
            viewModel.checkEmailDuplication(registerRequest.email).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        if (resource.data == false) {
                            Toast.makeText(requireContext(), "not dublicated", Toast.LENGTH_SHORT)
                                .show()
                            registerUser()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Email is already signed up",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is Resource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "error: ${resource.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    else -> {

                    }
                }

            }
        }
    }

    private fun registerUser() {
        lifecycleScope.launch {
            viewModel.registerUser(registerRequest).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        if(resource.data==true){
                            viewModel.sendEmailConfirmation(registerRequest.email)
                            Toast.makeText(requireContext(), "done", Toast.LENGTH_SHORT).show()
                            //findNavController().navigate(R.id.action_signUpFragment_to_confirmEmailFragment)
                        }else{
                            Toast.makeText(
                                requireContext(),
                                "Registration failed: ${resource.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is Resource.Error -> {
                        Log.d("alooo", "Registration failed: ${resource.message}")
                        Toast.makeText(
                            requireContext(),
                            "Registration failed: ${resource.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        Toast.makeText(
                            requireContext(),
                            "Registration else branch: ${resource.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }
        }
    }


    private fun validateRequest() {
        viewModel.validateSignUp(registerRequest)
        lifecycleScope.launch {
            viewModel.validationErrors.collect { errors ->
                val isErrorListEmpty = errors.values.all {
                    it == null
                }

                if (!isErrorListEmpty) {
                    binding.apply {
                        edtFirstName.error = errors[Constant.FIRST_NAME_FIELD]
                        edtLastName.error = errors[Constant.LAST_NAME_FIELD]
                        edtEmail.error = errors[Constant.EMAIL_FIELD]
                        edtPass.error = errors[Constant.PASS_FIELD]
                    }
                    Log.d("alooooo", errors.values.toString())

                } else {
                    checkEmailDuplication()
                }
            }
        }

    }


}