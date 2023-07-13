package com.example.pms.viewmodel.presentation_vm.chatting_vm.messages_vm

import android.content.Context

sealed class MessagesEvents {

    data class OnMessageChanging(
        val message: String
    ) : MessagesEvents()

    data class SendMessage(
        val message: String,
        val receiverId : Int ,
        val context: Context
    ) : MessagesEvents()

    data class OnStart(
        val context: Context,
        val receiverId: Int,
        val receiverUserName: String
    ) : MessagesEvents()


}