package com.example.pms.viewmodel.presentation_vm.profile_vm.my_posts_vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MyPostsScreenVM : ViewModel() {

    var state by mutableStateOf(MyPostsState())

    fun onEvent(event : MyPostsEvents) {
        when(event) {

        }
    }
}