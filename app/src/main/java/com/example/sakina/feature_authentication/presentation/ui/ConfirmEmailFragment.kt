package com.example.sakina.feature_authentication.presentation.ui

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
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentConfirmEmailBinding
import com.example.sakina.feature_authentication.domain.model.AuthenticateRequest
import com.example.sakina.feature_authentication.presentation.view_model.AuthViewModel
import kotlinx.coroutines.launch


class ConfirmEmailFragment : Fragment() {

    lateinit var binding: FragmentConfirmEmailBinding
    lateinit var viewModel: AuthViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).authViewModel


    }

    override fun onResume() {
        super.onResume()
        isEmailConfirmed()
    }

    private fun isEmailConfirmed() {
        lifecycleScope.launch {
            viewModel.authenticateUser(
                AuthenticateRequest(
                    MySharedPref.getString(Constant.USER_EMAIL, ""),
                    MySharedPref.getString(Constant.USER_PASS, "")
                )
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d("ConCon", "Resource.Success${resource.data} ")
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
                        findNavController().navigate(
                            R.id.action_confirmEmailFragment_to_homeFragment
                        )
                    }

                    is Resource.Error -> {
                        Log.d("ConCon", "Resource.Error ${resource.message}")
                        when (resource.message.toString()) {
                            Constant.EMAIL_IS_NOT_CONFIRMED -> Log.d("ConCon", "isEmailConfirmed: NOT ")

                            Constant.EMAIL_OR_PASSWORD_IS_NOT_CORRECT -> Log.d(
                                "ConCon",
                                "isEmailConfirmed: Pass"
                            )

                            else -> Log.d(
                                "ConCon",
                                "onViewCreated: Resource.Error  else ${resource.message.toString()} "
                            )

                        }
                        Log.d("ConCon", resource.message.toString())
                    }

                    else -> {
                    }
                }
            }
        }
    }


}