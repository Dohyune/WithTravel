package com.example.meetteam

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetteam.databinding.ActivityChattingBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.sidesheet.SideSheetBehavior
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ChattingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChattingBinding
    private lateinit var chatAdapter: MessageAdapter
    private val messagesList = mutableListOf<MessageData>()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chatTitle = intent.getStringExtra("CHAT_TITLE")
        binding.chatTitleTextView.text = chatTitle

        setupRecyclerView()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.optionsButton.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.END) // 오른쪽에서 슬라이드
        }

        binding.sendButton.setOnClickListener {
            sendMessage()
        }

        var isPlusClicked = false

        binding.plusButton.setOnClickListener {
            if (!isPlusClicked) {
                binding.bottomButtonGroup.visibility = View.VISIBLE
                binding.plusButton.setImageResource(R.drawable.ic_close) // X 버튼으로 변경
            } else {
                binding.bottomButtonGroup.visibility = View.GONE
                binding.plusButton.setImageResource(R.drawable.chatting_plus) // 다시 + 버튼으로 변경
            }
            isPlusClicked = !isPlusClicked
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
