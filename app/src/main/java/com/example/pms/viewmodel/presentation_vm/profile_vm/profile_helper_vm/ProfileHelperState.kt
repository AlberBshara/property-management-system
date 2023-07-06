package com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm


import com.example.pms.model.MyFavResponse

data class ProfileHelperState(
    var isLoading: Boolean = false,
    var needRefresh: Boolean = false,
    var postsList: List<MyFavResponse.PostData> = emptyList(),
     
)
