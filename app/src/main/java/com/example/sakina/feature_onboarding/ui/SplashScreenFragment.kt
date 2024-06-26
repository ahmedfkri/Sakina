package com.example.sakina.feature_onboarding.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.sakina.MainActivity
import com.example.sakina.R
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.ADVICE
import com.example.sakina.core.util.Constant.LOGGED_IN
import com.example.sakina.core.util.Constant.ON_BOARDING
import com.example.sakina.core.util.Constant.SIGNED_UP
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentSplashScreenBinding
import com.example.sakina.feature_advice.presentation.view_model.AdviceViewModel
import com.example.sakina.feature_authentication.presentation.view_model.AuthViewModel
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var adviceViewModel: AdviceViewModel
    lateinit var bundle: Bundle
    //private var count = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    /*    override fun onAttach(context: Context) {
            super.onAttach(context)

            authViewModel = (activity as MainActivity).authViewModel
            adviceViewModel = (activity as MainActivity).adviceViewModel

            setUpNavigation()

        }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = (activity as MainActivity).authViewModel
        adviceViewModel = (activity as MainActivity).adviceViewModel

        setUpNavigation()


    }

    private fun setUpNavigation() {
        Handler().postDelayed({
            if (isOnBoardingFinished()) {
                if (isUserSignedUp()) {
                    if (isUserLoggedIn()) {
                        view?.post {
                            findNavController().navigate(
                                R.id.action_splashScreenFragment_to_homeFragment
                            )
                        }

                    } else {
                        view?.post {
                            findNavController().navigate(
                                R.id.action_splashScreenFragment_to_loginFragment
                            )
                        }

                    }
                } else {
                    view?.post {
                        findNavController().navigate(R.id.action_splashScreenFragment_to_signUpFragment)
                    }
                }
            } else {
                view?.post {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
                }
            }

            getRandomAdviceData(12)

        }, 2000)
    }

    private fun getRandomAdviceData(count: Int) {
        val adviceId = (1..count).random()
        runBlocking {
            adviceViewModel.getAdvicesById(adviceId).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val gson = Gson()
                        val serializedObject = gson.toJson(resource.data)
                        MySharedPref.putString(ADVICE, serializedObject)
                        Log.d(TAG, "success:  done")
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireActivity(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                        Log.d(TAG, "error: ${resource.message} ")
                    }

                    else -> {
                        Log.d(TAG, "loading: ${resource.message} ")
                    }
                }
            }
        }
    }


    private fun isUserSignedUp() = MySharedPref.getBool(SIGNED_UP, false)
    private fun isUserLoggedIn() = MySharedPref.getBool(LOGGED_IN, false)

    private fun isOnBoardingFinished() = MySharedPref.getBool(ON_BOARDING, false)

}