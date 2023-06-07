package com.example.pms.viewmodel.presentation_vm.vehicles_vm.publish_vehicle_vm

import android.net.Uri

data class PublishVehicleState(
    var id: Int = -1,
    var showBottomSheet: Boolean = false,
    var indexOfCurrentImage: Int = 0,
    var scrolledY: Float = 0f,
    var previousOffset: Int = 0,
    var showYearDialogPicker: Boolean = false,
    var imageDetectionCaution: List<Int> = emptyList(),
    var showLocationPermission: Boolean = false,
    var showIndicator: Boolean = false,
    var showInternetAlert: Boolean = false,
    var requestInternetPermission: Boolean = false,
    var dataInvalid: Boolean = false,
    var showUploadingAllData : Boolean = false ,
    var done : Boolean = false ,
    var enteredData: EnteredData = EnteredData()
) {
    data class EnteredData(
        var listOfSelectedImages: List<Uri> = emptyList(),
        var brand: String = "",
        var secondaryBrand: String = "",
        var listingType: String = "",
        var transmissionType: String = "",
        var price: Float = 0f,
        var kilometer: Float = 0f,
        var location: String = "",
        var governorate: String = "",
        var manufactureYear: String = "",
        var condition: String = "",
        var description: String = "",
        var color: String = "",
        var fuelType: String = "",
        var derivingForce: String = ""
    ) {
        fun isValid(): Boolean =
            brand.isNotEmpty() && secondaryBrand.isNotEmpty() && listingType.isNotEmpty()
                    && transmissionType.isNotEmpty() && price != 0f && kilometer != 0f
                    && location.isNotEmpty() && governorate.isNotEmpty()
                    && manufactureYear.isNotEmpty() && condition.isNotEmpty()
                    && description.isNotEmpty() && color.isNotEmpty()
                    && fuelType.isNotEmpty() && derivingForce.isNotEmpty()
    }
}