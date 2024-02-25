package com.example.sakina.feature_chat.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sakina.core.util.Constant.BOT_MESSAGE_TYPE
import com.example.sakina.core.util.Constant.USER_MESSAGE_TYPE
import com.example.sakina.databinding.ItemBotMessageBinding
import com.example.sakina.databinding.ItemUserMessageBinding
import com.example.sakina.feature_chat.domain.model.ChatMessage

class ChatAdapter : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            USER_MESSAGE_TYPE -> UserMessageViewHolder.create(parent)
            BOT_MESSAGE_TYPE -> BotMessageViewHolder.create(parent)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when (holder) {
            is UserMessageViewHolder -> holder.userBind(message)
            is BotMessageViewHolder -> holder.botBind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isBotMessage) {
            BOT_MESSAGE_TYPE
        } else {
            USER_MESSAGE_TYPE
        }
    }

    class UserMessageViewHolder private constructor(private val binding: ItemUserMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun userBind(message: ChatMessage) {
            binding.txtUserMessage.text = message.message
        }

        companion object {
            fun create(parent: ViewGroup): UserMessageViewHolder {
                val binding =
                    ItemUserMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return UserMessageViewHolder(binding)
            }
        }
    }

    class BotMessageViewHolder private constructor(private val binding: ItemBotMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun botBind(message: ChatMessage) {
            binding.txtBotMessage.text = message.message
        }

        companion object {
            fun create(parent: ViewGroup): BotMessageViewHolder {
                val binding =
                    ItemBotMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return BotMessageViewHolder(binding)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChatMessage>() {
            override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
                return oldItem.timestamp == newItem.timestamp
            }

            override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
                return oldItem == newItem
            }
        }
    }
}
