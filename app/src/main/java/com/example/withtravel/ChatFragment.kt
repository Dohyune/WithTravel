package com.example.withtravel

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.withtravel.databinding.DialogCreateChatBinding
import com.example.withtravel.databinding.Fragment2ChattingBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class ChatFragment : Fragment() {

    private lateinit var binding: Fragment2ChattingBinding
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment2ChattingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatViewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)

        setupRecyclerView()

        chatViewModel.chatDataList.observe(viewLifecycleOwner) { chatList ->
            chatAdapter.submitList(chatList.toList())

            if (chatList.isEmpty()) {
                showEmptyChatViews()
            } else {
                hideEmptyChatViews()
            }
        }

        binding.createChat.setOnClickListener {
            showAddChatDialog()
        }

        binding.newChatButton.setOnClickListener {
            showAddChatDialog()
        }

        binding.testCreateChat.setOnClickListener {
            showLocalAddChatDialog()
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter { chatData ->
            openChatRoom(chatData)
        }
        binding.recyclerViewChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showAddChatDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogCreateChatBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(dialogBinding.root)

        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        var wantLeader = false // 기본값을 false로 설정

        dialogBinding.leaderToggle.setOnCheckedChangeListener { _, isChecked ->
            wantLeader = isChecked
        }

        // 채팅방 제목 입력 시 확인 버튼 활성화 및 색상 변경
        dialogBinding.editTextChatName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    dialogBinding.confirmButton.isEnabled = true
                    dialogBinding.confirmButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.blue)) // 파란색으로 변경
                } else {
                    dialogBinding.confirmButton.isEnabled = false
                    dialogBinding.confirmButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray)) // 회색으로 변경
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.confirmButton.setOnClickListener {
            val chatroomName = dialogBinding.editTextChatName.text.toString()
            val totalMember = dialogBinding.peopleCount.text.toString().toInt()
            //createChatRoom(chatroomName, totalMember, wantLeader)
            dialog.dismiss()
        }

        // 인원 수 조정
        dialogBinding.decreaseButton.setOnClickListener {
            val currentPeople = dialogBinding.peopleCount.text.toString().toInt()
            if (currentPeople > 2) {
                val newCount = currentPeople - 1
                dialogBinding.peopleCount.text = newCount.toString()

                if (newCount == 2) {
                    dialogBinding.decreaseButton.isEnabled = false
                    dialogBinding.decreaseButton.setBackgroundResource(R.drawable.chat_dialog_minus_n) // 마이너스 비활성화 아이콘
                } else {
                    dialogBinding.decreaseButton.setBackgroundResource(R.drawable.chat_dialog_minus) // 마이너스 활성화 아이콘
                }

                dialogBinding.increaseButton.isEnabled = true
                dialogBinding.increaseButton.setBackgroundResource(R.drawable.chat_dialog_plus) // 플러스 활성화 아이콘
            }
        }

        dialogBinding.increaseButton.setOnClickListener {
            val currentPeople = dialogBinding.peopleCount.text.toString().toInt()
            if (currentPeople < 7) {
                val newCount = currentPeople + 1
                dialogBinding.peopleCount.text = newCount.toString()

                if (newCount == 7) {
                    dialogBinding.increaseButton.isEnabled = false
                    dialogBinding.increaseButton.setBackgroundResource(R.drawable.chat_dialog_plus_n) // 플러스 비활성화 아이콘
                } else {
                    dialogBinding.increaseButton.setBackgroundResource(R.drawable.chat_dialog_plus) // 플러스 활성화 아이콘
                }

                dialogBinding.decreaseButton.isEnabled = true
                dialogBinding.decreaseButton.setBackgroundResource(R.drawable.chat_dialog_minus) // 마이너스 활성화 아이콘
            }
        }

        dialog.show()
    }

/*
    private fun createChatRoom(chatroomName: String, totalMember: Int, wantLeader: Boolean) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val request = CreateRoomRequest(chatroomName, totalMember, wantLeader)

        apiService.createRoom( request).enqueue(object : Callback<CreateRoomResponse> {
            override fun onResponse(call: Call<CreateRoomResponse>, response: Response<CreateRoomResponse>) {
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    val newChat = ChatData(chatroomName, response.body()?.result ?: "", totalMember.toString(), getCurrentTime())
                    chatViewModel.addChat(newChat)
                    Toast.makeText(requireContext(), "채팅방이 생성되었습니다!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "채팅방 생성 실패: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CreateRoomResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "채팅방 생성 중 오류 발생: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
*/
    private fun showLocalAddChatDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogCreateChatBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(dialogBinding.root)

        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        var wantLeader = false // 기본값을 false로 설정

        dialogBinding.leaderToggle.setOnCheckedChangeListener { _, isChecked ->
            wantLeader = isChecked
        }

        // 채팅방 제목 입력 시 확인 버튼 활성화 및 색상 변경
        dialogBinding.editTextChatName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    dialogBinding.confirmButton.isEnabled = true
                    dialogBinding.confirmButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.blue)) // 파란색으로 변경
                } else {
                    dialogBinding.confirmButton.isEnabled = false
                    dialogBinding.confirmButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.darkgray)) // 회색으로 변경
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

        dialogBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.confirmButton.setOnClickListener {
            val chatroomName = dialogBinding.editTextChatName.text.toString()
            val totalMember = dialogBinding.peopleCount.text.toString().toInt()
            createLocalChatRoom(chatroomName, totalMember, wantLeader)
            dialog.dismiss()
        }

        // 인원 수 조정
        dialogBinding.decreaseButton.setOnClickListener {
            val currentPeople = dialogBinding.peopleCount.text.toString().toInt()
            if (currentPeople > 2) {
                val newCount = currentPeople - 1
                dialogBinding.peopleCount.text = newCount.toString()

                if (newCount == 2) {
                    dialogBinding.decreaseButton.isEnabled = false
                    dialogBinding.decreaseButton.setBackgroundResource(R.drawable.chat_dialog_minus_n) // 마이너스 비활성화 아이콘
                } else {
                    dialogBinding.decreaseButton.setBackgroundResource(R.drawable.chat_dialog_minus) // 마이너스 활성화 아이콘
                }

                dialogBinding.increaseButton.isEnabled = true
                dialogBinding.increaseButton.setBackgroundResource(R.drawable.chat_dialog_plus) // 플러스 활성화 아이콘
            }
        }

        dialogBinding.increaseButton.setOnClickListener {
            val currentPeople = dialogBinding.peopleCount.text.toString().toInt()
            if (currentPeople < 7) {
                val newCount = currentPeople + 1
                dialogBinding.peopleCount.text = newCount.toString()

                if (newCount == 7) {
                    dialogBinding.increaseButton.isEnabled = false
                    dialogBinding.increaseButton.setBackgroundResource(R.drawable.chat_dialog_plus_n) // 플러스 비활성화 아이콘
                } else {
                    dialogBinding.increaseButton.setBackgroundResource(R.drawable.chat_dialog_plus) // 플러스 활성화 아이콘
                }

                dialogBinding.decreaseButton.isEnabled = true
                dialogBinding.decreaseButton.setBackgroundResource(R.drawable.chat_dialog_minus) // 마이너스 활성화 아이콘
            }
        }

        dialog.show()
    }


    private fun createLocalChatRoom(chatroomName: String, totalMember: Int, wantLeader: Boolean) {
        val chatCode = "ROOM" + Random.nextInt(10000, 99999).toString()
        val newChat = ChatData(chatroomName, chatCode, totalMember.toString(), getCurrentTime())
        chatViewModel.addChat(newChat)
        Toast.makeText(requireContext(), "로컬에서 채팅방 생성", Toast.LENGTH_SHORT).show()
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }

    private fun openChatRoom(chatData: ChatData) {
        val intent = Intent(requireContext(), ChattingActivity::class.java).apply {
            putExtra("CHAT_TITLE", chatData.chat_title)
            putExtra("CHAT_CODE", chatData.chat_code)
            putExtra("PEOPLE_NUM", chatData.people_num)
        }
        startActivity(intent)
    }


    private fun showEmptyChatViews() {
        binding.icEmptyChat.visibility = View.VISIBLE
        binding.emptyChatText.visibility = View.VISIBLE
        binding.newChatButton.visibility = View.VISIBLE
    }

    private fun hideEmptyChatViews() {
        binding.icEmptyChat.visibility = View.GONE
        binding.emptyChatText.visibility = View.GONE
        binding.newChatButton.visibility = View.GONE
    }
}
