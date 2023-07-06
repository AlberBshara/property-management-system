package com.example.pms.viewmodel.presentation_vm.chatting_vm.messages_vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MessagesScreenVM : ViewModel() {

    var state by mutableStateOf(MessagesState())

    fun onEvent(event: MessagesEvents) {

    }

}