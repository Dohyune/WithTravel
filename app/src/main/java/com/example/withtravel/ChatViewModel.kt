package com.example.withtravel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {
    private val _chatDataList = MutableLiveData<MutableList<ChatData>>(mutableListOf())
    val chatDataList: LiveData<MutableList<ChatData>> get() = _chatDataList

    fun addChat(chatData: ChatData) {
        _chatDataList.value?.add(0, chatData)
        _chatDataList.value = _chatDataList.value // Triggers LiveData update
    }
}
