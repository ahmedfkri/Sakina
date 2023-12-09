package com.example.sakina.feature_onboarding.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sakina.R
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.ON_BOARDING
import com.example.sakina.core.util.Constant.SIGNED
import com.example.sakina.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({
            if (isOnBoardingFinished()) {
                if (isUserSigned()) {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
                } else {
                    findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
                }
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
            }
        }, 4000)

    }

    private fun isUserSigned() = MySharedPref.getBool(SIGNED, false)

    private fun isOnBoardingFinished() = MySharedPref.getBool(ON_BOARDING, false)

}