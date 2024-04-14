package com.example.sakina

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.EXP_ON
import com.example.sakina.core.util.Constant.JWT_TOKEN
import com.example.sakina.core.util.Constant.LOGGED_IN
import com.example.sakina.core.util.Constant.REFRESH_TOKEN
import com.example.sakina.core.util.Constant.USER_EMAIL
import com.example.sakina.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loAccount.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_accountFragment)
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)
        }

        binding.loLogout.setOnClickListener {
            MySharedPref.clearValue(USER_EMAIL)
            MySharedPref.clearValue(JWT_TOKEN)
            MySharedPref.clearValue(REFRESH_TOKEN)
            MySharedPref.clearValue(EXP_ON)
            MySharedPref.putBool(LOGGED_IN, false)
            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
        }
    }
}