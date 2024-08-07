package com.example.meetteam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meetteam.databinding.ItemChatBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatViewHolder(binding:ItemChatBinding):RecyclerView.ViewHolder(binding.root) {
    val subject = binding.subject
    val code = binding.code
    val time = binding.time
    val peopleNum = binding.peopleNum
}