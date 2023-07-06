package com.example.pms.viewmodel.presentation_vm.chatting_vm.messages_vm

import android.content.Context

sealed class MessagesEvents {

    data class SendMessage(
        val message: String,
        val context : Context
    ) : MessagesEvents()


}