package com.example.meetteam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.meetteam.databinding.ItemChatBinding

class ChatAdapter(private val onChatClick: (ChatData) -> Unit) : ListAdapter<ChatData, ChatAdapter.ChatViewHolder>(ChatDiffCallback()) {

    class ChatViewHolder(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatData: ChatData, onChatClick: (ChatData) -> Unit) {
            binding.apply {
                subject.text = chatData.chat_title
                code.text = chatData.chat_code
                time.text = chatData.chat_time
                peopleNum.text = chatData.people_num

                root.setOnClickListener {
                    onChatClick(chatData)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatData = getItem(position)
        holder.bind(chatData, onChatClick)
    }
}

class ChatDiffCallback : DiffUtil.ItemCallback<ChatData>() {
    override fun areItemsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
        return oldItem.chat_code == newItem.chat_code
    }

    override fun areContentsTheSame(oldItem: ChatData, newItem: ChatData): Boolean {
        return oldItem == newItem
    }
}
