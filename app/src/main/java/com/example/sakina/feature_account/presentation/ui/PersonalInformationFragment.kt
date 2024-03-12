package com.example.sakina.feature_account.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sakina.R
import com.example.sakina.databinding.FragmentChangePasswordBinding
import com.example.sakina.databinding.FragmentPersonalInformationBinding
import com.example.sakina.feature_account.presentation.view_model.AccountViewModel

class PersonalInformationFragment : Fragment() {
    lateinit var binding:FragmentPersonalInformationBinding
    lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentPersonalInformationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}