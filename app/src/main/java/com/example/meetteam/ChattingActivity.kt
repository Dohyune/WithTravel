package com.example.meetteam

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetteam.databinding.ActivityChattingBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ChattingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChattingBinding
    private lateinit var chatAdapter: MessageAdapter
    private val messagesList = mutableListOf<MessageData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chatTitle = intent.getStringExtra("CHAT_TITLE")
        binding.chatTitleTextView.text = chatTitle

        setupRecyclerView()

        binding.sendButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = MessageAdapter(messagesList)
        binding.recyclerViewMessages.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@ChattingActivity)
        }
    }

    private fun sendMessage() {
        val messageText = binding.messageEditText.text.toString()
        if (messageText.isNotEmpty()) {
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().time)
            val newMessage = MessageData(messageText, currentTime)
            messagesList.add(newMessage)
            chatAdapter.notifyItemInserted(messagesList.size - 1)
            binding.recyclerViewMessages.scrollToPosition(messagesList.size - 1)
            binding.messageEditText.text.clear()
        }
    }
}
