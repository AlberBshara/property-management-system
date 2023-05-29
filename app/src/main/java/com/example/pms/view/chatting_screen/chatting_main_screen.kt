package com.example.pms.view.chatting_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.presentation_vm.chatting_vm.ChattingScreenVM
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pms.view.animation.ProgressAnimatedBar
import com.example.pms.viewmodel.presentation_vm.chatting_vm.ChattingEvents

@Composable
fun ChattingMainScreen(
    navController: NavHostController,
    viewModel: ChattingScreenVM = viewModel()
) {

    val state = viewModel.state

    viewModel.onEvent(ChattingEvents())




    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 53.dp)
    ) {

        items(state.chattingItemList) {
            if (state.isLoading){
                ProgressAnimatedBar(isLoading = true,  Modifier.size(60.dp))
            }
            ChattingItemCard(
                onClinkListener = {

                },
                chattingItemData = it,
                onCallingPhoneListener = {
                }
            )
        }
    }
}