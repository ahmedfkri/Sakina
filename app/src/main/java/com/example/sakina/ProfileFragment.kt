package com.example.sakina

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.FIRST_NAME
import com.example.sakina.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.txtUserName.text = MySharedPref.getString(FIRST_NAME, "")

        binding.btnPersonalInfo.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_personalInformationFragment)

        }

        binding.btnMedicalInfo.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_medicalInfoFragment)

        }
    }
}