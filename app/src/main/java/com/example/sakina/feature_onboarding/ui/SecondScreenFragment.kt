package com.example.sakina.feature_onboarding.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sakina.R
import com.example.sakina.databinding.FragmentSecondScreenBinding


class SecondScreenFragment : Fragment() {

    lateinit var binding: FragmentSecondScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSecondScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

}