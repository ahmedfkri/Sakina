package com.example.sakina.feature_authentication.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sakina.MainActivity
import com.example.sakina.R
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant
import com.example.sakina.databinding.FragmentLoginBinding
import com.example.sakina.feature_authentication.domain.mode.AuthenticateRequest
import com.example.sakina.feature_authentication.presentation.view_model.AuthViewModel


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

        if(isUserLoggedIn()){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        binding.btnLogin.setOnClickListener {
            MySharedPref.putString(Constant.USER_EMAIL,binding.edtEmail.text.toString())
            MySharedPref.putString(Constant.USER_PASS,binding.edtPass.text.toString())
            MySharedPref.putBool(Constant.LOGGED_IN,true)
            viewModel.authenticateUser(AuthenticateRequest(binding.edtEmail.text.toString(),binding.edtPass.text.toString()))
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.txtSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }
    private fun isUserLoggedIn() = MySharedPref.getBool(Constant.LOGGED_IN, false)
}
