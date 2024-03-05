package com.example.sakina.feature_chat.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentChatBinding
import com.example.sakina.feature_chat.data.repository.ChatRepositoryImpl
import com.example.sakina.feature_chat.domain.model.ChatMessage
import com.example.sakina.feature_chat.domain.model.MessageRequest
import com.example.sakina.feature_chat.domain.use_case.SendMessageUseCase
import com.example.sakina.feature_chat.presentation.adapter.ChatAdapter
import com.example.sakina.feature_chat.presentation.view_model.ChatViewModel
import com.example.sakina.feature_chat.presentation.view_model.ChatViewModelFactory
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding
    lateinit var viewModel: ChatViewModel
    lateinit var chatAdapter: ChatAdapter
    private var messageList = mutableListOf<ChatMessage>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = ChatRepositoryImpl()
        val sendMessageUseCase = SendMessageUseCase(repository)
        viewModel =
            ViewModelProvider(requireActivity(), ChatViewModelFactory(sendMessageUseCase)).get(
                ChatViewModel::class.java
            )

        setupRecyclerView()


        binding.btnSend.setOnClickListener {
            if (binding.edtMessage.text.isNotEmpty()) {
                val message = binding.edtMessage.text.toString()
                binding.edtMessage.text.clear()
                messageList.add(ChatMessage(message, false, System.currentTimeMillis()))
                chatAdapter.submitList(messageList)
                val messageRequest = MessageRequest(message)
                sendMessage(messageRequest)
            }

        }

        binding.rvChat.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                binding.rvChat.viewTreeObserver.removeOnPreDrawListener(this)
                binding.rvChat.scrollToPosition(messageList.size - 1)
                return true
            }
        })

        binding.edtMessage.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.rvChat.scrollToPosition(messageList.size - 1)
            }
        }


    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter()
        binding.rvChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun sendMessage(message: MessageRequest) {

        lifecycleScope.launch {
            viewModel.sendMessage(message).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        messageList.removeLast()
                        messageList.add(resource.data!!)
                        chatAdapter.submitList(messageList)
                        chatAdapter.notifyDataSetChanged()
                        binding.rvChat.smoothScrollToPosition(messageList.size - 1)

                    }

                    is Resource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Error " + resource.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        Log.d(TAG, "sendMessage: " + resource.message)
                    }

                    else -> {
                        messageList.add(ChatMessage("• • •", true, System.currentTimeMillis()))
                        chatAdapter.submitList(messageList)

                    }
                }

            }

        }

    }
}