package com.example.sakina.feature_onboarding.ui

import android.os.Bundle
import android.os.Handler
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
import com.example.sakina.core.util.Constant.EMAIL_IS_NOT_CONFIRMED
import com.example.sakina.core.util.Constant.EMAIL_OR_PASSWORD_IS_NOT_CORRECT
import com.example.sakina.core.util.Constant.LOGGED_IN
import com.example.sakina.core.util.Constant.ON_BOARDING
import com.example.sakina.core.util.Constant.SIGNED_UP
import com.example.sakina.core.util.Constant.USER_EMAIL
import com.example.sakina.core.util.Constant.USER_PASS
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentSplashScreenBinding
import com.example.sakina.feature_authentication.domain.model.AuthenticateRequest
import com.example.sakina.feature_authentication.presentation.view_model.AuthViewModel
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = (activity as MainActivity).authViewModel

        Handler().postDelayed({
            if (isOnBoardingFinished()) {
                if (isUserSignedUp()) {
                    lifecycleScope.launch {
                        authViewModel.authenticateUser(
                            AuthenticateRequest(
                                MySharedPref.getString(USER_EMAIL, ""),
                                MySharedPref.getString(USER_PASS, "")
                            )
                        ).collect { resource ->
                            when (resource) {
                                is Resource.Success -> {
                                    Log.d("Splashsss", "onViewCreated: Resource.Success ")
                                    val authResponse = resource.data
                                    MySharedPref.putBool(LOGGED_IN, true)
                                    MySharedPref.putString(
                                        Constant.JWT_TOKEN,
                                        authResponse?.jwtToken ?: ""
                                    )
                                    MySharedPref.putString(
                                        Constant.REFRESH_TOKEN,
                                        authResponse?.refreshToken ?: ""
                                    )
                                    findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
                                }

                                is Resource.Error -> {
                                    Log.d("Splashsss", "onViewCreated: Resource.Error ")
                                    when (resource.message.toString()) {
                                        EMAIL_IS_NOT_CONFIRMED -> findNavController().navigate(
                                            R.id.action_splashScreenFragment_to_confirmEmailFragment
                                        )

                                        EMAIL_OR_PASSWORD_IS_NOT_CORRECT -> findNavController().navigate(
                                            R.id.action_splashScreenFragment_to_loginFragment
                                        )

                                        else -> Log.d("Splashsss", "onViewCreated: Resource.Error  else ${resource.message.toString()} ")

                                    }
                                    Log.d("Splashsss", resource.message.toString())
                                }

                                else -> {
                                }
                            }
                        }
                    }
                } else {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_signUpFragment)
                }
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
            }
        }, 2000)


    }

    private fun isUserSignedUp() = MySharedPref.getBool(SIGNED_UP, false)
    private fun isUserLoggedIn() = MySharedPref.getBool(LOGGED_IN, false)

    private fun isOnBoardingFinished() = MySharedPref.getBool(ON_BOARDING, false)

}