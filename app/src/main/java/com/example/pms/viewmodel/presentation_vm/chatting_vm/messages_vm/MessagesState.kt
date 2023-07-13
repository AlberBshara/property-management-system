package com.example.pms.viewmodel.presentation_vm.chatting_vm.messages_vm

import com.example.pms.model.dto.ChatMessage

data class MessagesState(
    var chatMessages: List<ChatMessage> = listOf(),
    var isLoading: Boolean = false,
    var username: String = "",
    var currentMessage : String =  ""
)


