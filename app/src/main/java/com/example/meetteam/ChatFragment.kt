package com.example.meetteam

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetteam.databinding.FragmentChatBinding
import com.example.meetteam.network.ApiService
import com.example.meetteam.network.CreateRoomRequest
import com.example.meetteam.network.CreateRoomResponse
import com.example.meetteam.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

        binding.additionChat.setOnClickListener {
            //코드입력함수 짜기
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
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_create_chat, null)

        dialog.setContentView(dialogView)

        // 다이얼로그 크기 및 배경 설정 (양 옆에 마진 추가)
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(), // 화면 너비의 90%만큼 다이얼로그 크기 설정
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog_background)

        // 다이얼로그 타이틀을 중앙에 배치
        val titleTextView = dialogView.findViewById<TextView>(R.id.dialogTitle)
        titleTextView.gravity = Gravity.CENTER

        val titleInput = dialogView.findViewById<EditText>(R.id.editTextChatName)
        val peopleNumInput = dialogView.findViewById<EditText>(R.id.editPeopleNum)
        val leaderToggle = dialogView.findViewById<Switch>(R.id.leader_toggle)
        val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)

        var wantLeader = false

        leaderToggle.setOnCheckedChangeListener { _, isChecked ->
            wantLeader = isChecked
        }

        confirmButton.setOnClickListener {
            val chatroomName = titleInput.text.toString()
            val totalMember = peopleNumInput.text.toString().toIntOrNull()

            if(chatroomName.isNotEmpty() && totalMember != null && totalMember in 2..7){
                createChatRoom(chatroomName,totalMember,wantLeader)
                dialog.dismiss()
            } else{
                Toast.makeText(requireContext(), "인원수는 2~7명 사이여야 합니다.",Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    private fun createChatRoom(chatroomName: String, totalMember: Int, wantLeader: Boolean){
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val request = CreateRoomRequest(chatroomName, totalMember, wantLeader)

        apiService.createRoom(request).enqueue(object : Callback<CreateRoomResponse> {
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

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = Calendar.getInstance().time
        return dateFormat.format(currentTime)
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
