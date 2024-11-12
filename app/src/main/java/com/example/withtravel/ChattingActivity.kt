package com.example.withtravel

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.withtravel.databinding.ActivityChattingBinding
import java.text.SimpleDateFormat
import java.util.*

class ChattingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChattingBinding
    private lateinit var chatAdapter: MessageAdapter
    private val messagesList = mutableListOf<MessageData>()
    private var isPlusClicked = false
    private var keyboardHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 채팅 제목 설정
        val chatTitle = intent.getStringExtra("CHAT_TITLE")
        binding.chatTitleTextView.text = chatTitle

        setupRecyclerView()

        // 플러스 버튼 클릭 이벤트
        binding.plusButton.setOnClickListener {
            togglePlusButton()
        }

        // 키보드 높이 감지
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            binding.root.getWindowVisibleDisplayFrame(rect)
            val screenHeight = binding.root.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            // 키보드가 올라왔는지 확인
            if (keypadHeight > screenHeight * 0.15) {
                // 키보드 높이 저장
                keyboardHeight = keypadHeight
                adjustBottomButtonGroupForKeyboard(keyboardHeight)
            } else {
                // 키보드가 내려갔을 때
                hideBottomButtonGroup()
            }
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = MessageAdapter(messagesList)
        binding.recyclerViewMessages.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(this@ChattingActivity)
        }
    }

    // 플러스 버튼 토글 동작
    private fun togglePlusButton() {
        if (!isPlusClicked) {
            binding.bottomButtonGroup.visibility = View.VISIBLE
            binding.plusButton.setImageResource(R.drawable.ic_close) // X 버튼으로 변경
            adjustBottomButtonGroupForKeyboard(keyboardHeight)
        } else {
            binding.bottomButtonGroup.visibility = View.GONE
            binding.plusButton.setImageResource(R.drawable.chatting_plus) // 다시 + 버튼으로 변경
        }
        isPlusClicked = !isPlusClicked
    }

    // 키보드 높이에 맞게 하단 버튼 그룹 위치 조정
    private fun adjustBottomButtonGroupForKeyboard(keyboardHeight: Int) {
        val layoutParams = binding.bottomButtonGroup.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.height = keyboardHeight
        binding.bottomButtonGroup.layoutParams = layoutParams
    }

    // 키보드가 내려가면 버튼 그룹 숨기기
    private fun hideBottomButtonGroup() {
        binding.bottomButtonGroup.visibility = View.GONE
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
