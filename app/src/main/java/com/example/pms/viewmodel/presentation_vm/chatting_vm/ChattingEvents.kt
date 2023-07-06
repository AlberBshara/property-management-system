package com.example.pms.viewmodel.presentation_vm.chatting_vm

import android.content.Context

sealed class ChattingEvents {

    object OnSearchStateChanged
        : ChattingEvents()

    data class OnStart(
        val context: Context
    ) : ChattingEvents()

    data class OnRefreshClicked(
        val context: Context
    ) : ChattingEvents()
}