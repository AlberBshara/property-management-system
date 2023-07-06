package com.example.pms.viewmodel.presentation_vm.chatting_vm

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pms.model.ChattingItemData
import com.example.pms.viewmodel.api.chatting_services.ChattingServicesImpl
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch

class ChattingScreenVM(
    private val chattingServicesImpl: ChattingServicesImpl = ChattingServicesImpl()
) : ViewModel() {

    var state by mutableStateOf(ChattingState())

    private var counter = 0

    fun onEvent(event: ChattingEvents) {
        when (event) {
            is ChattingEvents.OnStart -> {
                //fetchAllChatted(event.context)
                val list = listOf(
                    ChattingItemData(id = 1, username = "Alber bshara", phoneNumber = "09342"),
                    ChattingItemData(id = 1, username = "Alber bshara", phoneNumber = "09342"),
                    ChattingItemData(id = 1, username = "Alber bshara", phoneNumber = "09342"),
                    ChattingItemData(id = 1, username = "Alber bshara", phoneNumber = "09342")
                )
                state = state.copy(
                    chattingItemList = list
                )
            }
            is ChattingEvents.OnRefreshClicked -> {
                refresh(event.context)
            }
            is ChattingEvents.OnSearchStateChanged -> {
                state = state.copy(
                    showSearchBar = !state.showSearchBar
                )
            }
        }
    }


    private fun fetchAllChatted(
        context: Context
    ) {
        if (counter == 0) {
            this.counter++
            viewModelScope.launch {
                val chattedResponse = chattingServicesImpl.fetchAllChattedPersons(
                    TokenManager.getInstance(context).getToken()
                )
                chattedResponse.collect {
                    when (it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = it.isLoading
                            )
                        }
                        is Resource.Success -> {
                            it.data?.let { result ->
                                if (result.success) {
                                    state = if (result.chattedList.isEmpty()) {
                                        state.copy(
                                            noChattingYet = true
                                        )
                                    }else{
                                        val chattingItems = result.chattedList.map { list ->
                                            list.toChattingListItem()
                                        }
                                        state.copy(
                                            chattingItemList = chattingItems
                                        )
                                    }
                                }
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                needRefresh = true
                            )
                        }
                    }
                }
            }
        }
    }

    private fun refresh(
        context: Context
    ) {
        this.counter = 0
        state = state.copy(
            needRefresh = false
        )
        fetchAllChatted(context)
    }
}