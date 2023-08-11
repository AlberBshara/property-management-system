package com.example.pms.viewmodel.presentation_vm.estates_vm.view_more_estate_vm

import android.content.Context
import androidx.navigation.NavHostController

sealed class ViewMoreEstateEvents {

    data class OnGetDataFromServer(
        val context: Context,
        val estateId: Int
    ) : ViewMoreEstateEvents()

    data class OnCurrentImageIndexChanged(
        val index: Int
    ) : ViewMoreEstateEvents()

    data class OnCallPhoneClicked(
        val phoneNumber: String,
        val context: Context
    ) : ViewMoreEstateEvents()

    data class OnShareClicked(
        val context: Context
    ) : ViewMoreEstateEvents()

    data class OnLikeClicked(
        val context: Context,
        val estateId: Int
    ) : ViewMoreEstateEvents()

    data class OnVisitProfileClicked(
        val navHostController: NavHostController
    ) : ViewMoreEstateEvents()

    object OnChangeShowRateScreen : ViewMoreEstateEvents()

    data class ChangeImage(val indexOfImage: Int) : ViewMoreEstateEvents()

    data class OnAddRateEstate(val estateId: Int, val rate: Int, val context: Context) :
        ViewMoreEstateEvents()

    data class OnChattingClicked(
        val navController : NavHostController ,
        val receiverId : Int ,
        val receiverUsername : String ,
        val receiverImageUrl : String?
    ) : ViewMoreEstateEvents()
}
