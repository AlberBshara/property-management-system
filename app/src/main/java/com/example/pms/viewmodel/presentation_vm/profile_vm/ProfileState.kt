package com.example.pms.viewmodel.presentation_vm.profile_vm

data class ProfileState(
    var isLoading: Boolean = false,
    var name: String = "",
    var email: String = "",
    var phone: String = "",
    var location: String = "",
    var imageUrl: String? = null,
    var facebookLink: String? = null,
    var instagramLink: String? = null,
    var twitterLink: String? = null,
    var isRefreshing: Boolean = false,
    var timeOut: Boolean = false,


    val faceBookPress: Boolean = false,
    val instagramPress: Boolean = false,
    val twitterPress: Boolean = false,
    val loading: Boolean = false,

)