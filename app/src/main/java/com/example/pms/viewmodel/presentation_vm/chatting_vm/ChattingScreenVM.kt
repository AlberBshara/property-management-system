package com.example.pms.viewmodel.presentation_vm.chatting_vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pms.model.ChattingItemData
import kotlinx.coroutines.launch

class ChattingScreenVM : ViewModel() {

    var state by mutableStateOf(ChattingState())



    fun onEvent(events: ChattingEvents) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            val newList = listOf(
                ChattingItemData(1,null, "Alber bshara",
                    null, "0947424243"),
                ChattingItemData(2,null, "Jojo",
                    null, "0947424243"),
                ChattingItemData(3,null, "Fadi",
                    null, "0947424243"),
                ChattingItemData(4,null, "Ahmad",
                    null, "0947424243")
            )
            state = state.copy(
                chattingItemList = newList,
                isLoading = false
            )
        }
    }
}