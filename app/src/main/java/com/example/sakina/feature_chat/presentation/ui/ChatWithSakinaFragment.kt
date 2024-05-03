package com.example.sakina.feature_chat.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sakina.R
import com.example.sakina.databinding.FragmentChatWithSakinaBinding


class ChatWithSakinaFragment : Fragment() {
    private lateinit var binding: FragmentChatWithSakinaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatWithSakinaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_chatWithSakinaFragment_to_homeFragment)
        }
        binding.startBtn.setOnClickListener {
            findNavController().navigate(R.id.action_chatWithSakinaFragment_to_chatFragment)
        }
    }

}