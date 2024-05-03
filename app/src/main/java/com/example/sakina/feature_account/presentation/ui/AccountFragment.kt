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
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.core.util.Constant.USER_EMAIL
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentAccountBinding
import com.example.sakina.feature_account.presentation.view_model.AccountViewModel
import kotlinx.coroutines.launch


class AccountFragment : Fragment() {
    lateinit var binding: FragmentAccountBinding
    lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = (activity as MainActivity).accountViewModel
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /*  viewModel.currentPass.observe(viewLifecycleOwner) { password ->
              binding.edtPass.setText(password)
          }
          *
         */

        getAccountData()

        binding.txtEmail.text = MySharedPref.getString(USER_EMAIL, "")



        binding.loName.setEndIconOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_changeNameFragment)
        }
        binding.loPassword.setEndIconOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_settingsFragment)
        }

    }

    private fun getAccountData() {
        lifecycleScope.launch {
            viewModel.getAccountData().collect { resourse ->
                when (resourse) {
                    is Resource.Success -> {
                        val name = resourse.data!!.firstName + " " + resourse.data.lastName
                        binding.edtName.setText(
                            name
                        )
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), resourse.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {
                        Log.d(TAG, "getAccountData: loading")
                    }
                }

            }
        }
    }

}