package com.example.pms.viewmodel.presentation_vm.profile_vm.profile_helper_vm


import com.example.pms.model.MyFavResponse
import com.example.pms.model.MyPostsModels

data class ProfileHelperState(
    var isLoading: Boolean = false,
    var needRefresh: Boolean = false,
    var vehiclesPostsList: List<MyFavResponse.PostData> = emptyList(),
    var estatesPostsList: List<MyPostsModels.MyEstatesPostResponse.PostDataEstates> = emptyList(),
    var noResult : Boolean = false,
    var isDeleting : Boolean = false ,
)
