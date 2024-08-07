package com.example.meetteam

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetteam.databinding.FragmentChatBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatAdapter: ChatAdapter

    // 채팅 데이터를 담을 리스트
    private val chatDataList = arrayListOf<ChatData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        setupRecyclerView()

        // 새로운 채팅 추가 버튼 클릭 리스너 설정
        binding.createChat.setOnClickListener {
            showAddChatDialog()
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(chatDataList)
        binding.recyclerViewChat.apply {
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        // 어댑터의 아이템 클릭 리스너 설정
        chatAdapter.setOnItemClickListener(object : ChatAdapter.OnItemClickListener {
            override fun onItemClick(chatData: ChatData) {
                val intent = Intent(requireContext(), ChatDetailActivity::class.java).apply {
                    putExtra("chatTitle", chatData.chat_title)
                }
                startActivity(intent)
            }
        })
    }

    private fun showAddChatDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_create_chat, null)
        val titleInput = dialogView.findViewById<EditText>(R.id.editTextChatName)
        val peopleNumInput = dialogView.findViewById<EditText>(R.id.editPeopleNum)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("새 채팅방 추가")
            .setPositiveButton("확인") { dialog, _ ->
                val title = titleInput.text.toString().trim()
                val peopleNum = peopleNumInput.text.toString().trim()

                // 입력값 검증
                if (title.isEmpty() || peopleNum.isEmpty()) {
                    Toast.makeText(requireContext(), "모든 필드를 채워주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    val code = Random.nextInt(1000, 10000).toString()
                    val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().time)

                    // 새로운 채팅 데이터 추가
                    chatDataList.add(0, ChatData(title, code, peopleNum, time))
                    chatAdapter.notifyItemInserted(0)
                    binding.recyclerViewChat.scrollToPosition(0)

                    dialog.dismiss()
                }
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }

        dialogBuilder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}