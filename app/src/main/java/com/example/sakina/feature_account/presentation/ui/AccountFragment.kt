package com.example.sakina.feature_account.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sakina.MainActivity
import com.example.sakina.R
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.USER_EMAIL
import com.example.sakina.databinding.FragmentAccountBinding
import com.example.sakina.feature_account.presentation.view_model.AccountViewModel


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


        viewModel.currentName.observe(viewLifecycleOwner) { name ->
            binding.edtName.setText(name)
        }
      /*  viewModel.currentPass.observe(viewLifecycleOwner) { password ->
            binding.edtPass.setText(password)
        }
        */
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

}