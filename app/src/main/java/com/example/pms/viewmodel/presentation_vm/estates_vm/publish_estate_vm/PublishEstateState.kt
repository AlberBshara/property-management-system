package com.example.pms.viewmodel.presentation_vm.estates_vm.publish_estate_vm

import android.net.Uri

data class PublishEstateState(

    var indexOfCurrentImage: Int = 0,
    var enteredData: EnteredData = EnteredData(),
    var fakeImagesIndexes: List<Int> = emptyList(),
    var isLoading: Boolean = false,
    var dataInvalid: Boolean = false,
    var successSendData: Boolean = false,
    var showInternetAlert: Boolean = false,


    ) {


    data class EnteredData(
        var listOfSelectedImages: List<Uri> = emptyList(),
        val governorate: String = "",
        val address: String = "",
        val estateType: String = "",
        val operationType: String = "",
        val price: String = "",
        val space: Float = 0f,
        val beds: String = "0",
        val baths: String = "0",
        val garage: String = "0",
        val levels: String = "0",
        val description: String = "",
        val statusOFEstate: String = ""

    )

}
