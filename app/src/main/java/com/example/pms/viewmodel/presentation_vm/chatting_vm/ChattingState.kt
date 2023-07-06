package com.example.pms.viewmodel.presentation_vm.chatting_vm

import com.example.pms.model.ChattingItemData

data class ChattingState(
    var chattingItemList: List<ChattingItemData> = emptyList(),
    var isLoading: Boolean = false,
    var showSearchBar: Boolean = false,
    var query: String = "",
    var needRefresh: Boolean = false,
    var noChattingYet: Boolean = false
)