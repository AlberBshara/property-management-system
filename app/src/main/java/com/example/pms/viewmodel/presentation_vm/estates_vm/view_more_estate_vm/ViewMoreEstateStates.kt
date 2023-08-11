package com.example.pms.viewmodel.presentation_vm.estates_vm.view_more_estate_vm

import com.example.pms.model.EstateViewMoreData

data class ViewMoreEstateStates(

    val loved: Boolean = false,
    val price: Int = 0,
    val governorate: String = "",
    val beds: Int = 0,
    val baths: Int = 0,
    val garage: Int = 0,
    val levels: Int = 0,
    val rate: Float = 0.0f,
    val estateType: String = "",
    val operationType: String = "",
    val status: String = "",
    val space: Int = 0,
    val address: String = "",
    val description: String = "",
    val name: String = "",
    val number: String = "",
    val userId: Int = -1,
    val userImageUrl: String? = null,
    val numberOfLikes: Int = 0,
    var imagesList: List<EstateViewMoreData.ListOfImages> = emptyList(),
    var currentImageIndex: Int = 0,
    var isLoading: Boolean = false,
    var isShowingRateScreen: Boolean = false

)
