package com.example.withtravel

import androidx.recyclerview.widget.RecyclerView
import com.example.withtravel.databinding.ItemChatBinding

class ChatViewHolder(binding:ItemChatBinding):RecyclerView.ViewHolder(binding.root) {
    val subject = binding.subject
    val code = binding.code
    val time = binding.time
    val peopleNum = binding.peopleNum
}