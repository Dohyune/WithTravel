package com.example.meetteam

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetteam.databinding.FragmentChatBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel을 Activity 범위에서 가져옴
        chatViewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)

        setupRecyclerView()

        chatViewModel.chatDataList.observe(viewLifecycleOwner) { chatList ->
            chatAdapter.submitList(chatList.toList())
        }

        binding.createChat.setOnClickListener {
            showAddChatDialog()
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
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_create_chat, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("새 채팅방 추가")
            .setPositiveButton("확인") { dialog, _ ->
                val titleInput = dialogView.findViewById<EditText>(R.id.editTextChatName)
                val peopleNumInput = dialogView.findViewById<EditText>(R.id.editPeopleNum)
                val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val currentTime = Calendar.getInstance().time

                val title = titleInput.text.toString()
                val code = Random.nextInt(1000, 10000).toString()
                val time = dateFormat.format(currentTime)
                val peopleNum = peopleNumInput.text.toString().toIntOrNull()

                if (peopleNum != null && peopleNum in 2..7) {
                    val newChat = ChatData(title, code, peopleNum.toString(), time)
                    chatViewModel.addChat(newChat)
                    dialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "인원수는 2~7명 사이여야 합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }

        dialogBuilder.create().show()
    }

    private fun openChatRoom(chatData: ChatData) {
        val intent = Intent(requireContext(), ChattingActivity::class.java).apply {
            putExtra("CHAT_TITLE", chatData.chat_title)
            putExtra("CHAT_CODE", chatData.chat_code)
            putExtra("PEOPLE_NUM", chatData.people_num)
        }
        startActivity(intent)
    }

}
