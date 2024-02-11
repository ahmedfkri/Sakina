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
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant
import com.example.sakina.core.util.Constant.EMAIL_FIELD
import com.example.sakina.core.util.Constant.PASS_FIELD
import com.example.sakina.core.util.Constant.USER_EMAIL
import com.example.sakina.core.util.Constant.USER_PASS
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentLoginBinding
import com.example.sakina.feature_authentication.domain.model.AuthenticateRequest
import com.example.sakina.feature_authentication.presentation.view_model.AuthViewModel
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).authViewModel

        binding.btnLogin.setOnClickListener {
           isUserLoggedIn()
        }
        binding.txtSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
}
    private fun isUserLoggedIn() {
        lifecycleScope.launch {
            viewModel.authenticateUser(
                AuthenticateRequest(
                    binding.edtEmail.text.toString(),
                    binding.edtPass.text.toString()
                )
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d("login", "onViewCreated: Resource.Success ")
                        val authResponse = resource.data
                        MySharedPref.putBool(Constant.LOGGED_IN, true)
                        MySharedPref.putString(
                            Constant.JWT_TOKEN,
                            authResponse?.jwtToken ?: ""
                        )
                        MySharedPref.putString(
                            Constant.REFRESH_TOKEN,
                            authResponse?.refreshToken ?: ""
                        )
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }

                    is Resource.Error -> {
                        Log.d("login", "onViewCreated: Resource.Error ")
                        when (resource.message.toString()) {
                            Constant.EMAIL_IS_NOT_CONFIRMED -> findNavController().navigate(
                                R.id.action_loginFragment_to_confirmEmailFragment
                            )
                            Constant.EMAIL_OR_PASSWORD_IS_NOT_CORRECT ->
                                Toast.makeText(
                                    requireContext(),
                                    "email or password is not correct please try again",
                                    Toast.LENGTH_LONG
                                ).show()
                            else -> Log.d(
                                "login",
                                "onViewCreated: Resource.Error  else ${resource.message.toString()} "
                            )

                        }
                        Log.d("login", resource.message.toString())
                    }

                    else -> {
                    }
                }
            }
        }
    }
}


