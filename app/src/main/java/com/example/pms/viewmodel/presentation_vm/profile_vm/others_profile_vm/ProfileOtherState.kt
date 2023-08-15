package com.example.pms.viewmodel.presentation_vm.profile_vm.others_profile_vm

import com.example.pms.model.GetAllEstateResponse
import com.example.pms.model.MyFavResponse

data class ProfileOtherState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    var imageUrl: String? = null,
    val numberOfPosts: String = "",
    val faceBookURL: String = "",
    val instagramURL: String = "",
    val twitterURL: String = "",

    var estatesPostsList: List<GetAllEstateResponse.PostData> = emptyList(),

    var isLoadingProfileData: Boolean = false,
    var isLoadingEstateCar: Boolean = false,
    var needRefresh: Boolean = false,
    var vehiclesPostsList: List<MyFavResponse.PostData> = emptyList(),
    var noResult: Boolean = false,

    var estateClicked: Boolean = true,
    var carClicked: Boolean = false,

    val faceBookPress: Boolean = false,
    val instagramPress: Boolean = false,
    val twitterPress: Boolean = false,
)