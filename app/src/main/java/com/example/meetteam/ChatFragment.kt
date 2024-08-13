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
    private lateinit var binding: FragmentChatBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var chatAdapter: ChatAdapter

    private val chatDataList = arrayListOf<ChatData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

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

                val peopleNum = peopleNumInput.text.toString().toIntOrNull() // Int로 변환하되, null이 될 수 있음

                if (peopleNum != null && peopleNum in 2..7) {
                    // 새로운 채팅 데이터 추가
                    chatDataList.add(0, ChatData(title, code, peopleNum.toString(), time))
                    chatAdapter.notifyItemInserted(0)
                    dialog.dismiss()
                } else {
                    // 인원이 2~7 사이가 아닐 때
                    Toast.makeText(requireContext(), "인원수는 2~7명 사이여야 합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }

        dialogBuilder.create().show()
    }
}