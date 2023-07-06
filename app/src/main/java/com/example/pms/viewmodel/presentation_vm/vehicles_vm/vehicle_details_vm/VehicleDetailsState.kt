package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_details_vm

import com.example.pms.model.VehicleViewMoreData

data class VehicleDetailsState(
    var isLoading: Boolean = false,
    var carId: Int = -1,
    var ownerId: Int = -1,
    var imagesList: List<VehicleViewMoreData.ImageData> = emptyList(),
    var currentImageIndex: Int = 0,
    var brand: String = "",
    var secondBrand: String = "",
    var operationType: String = "",
    var transmissionType: String = "",
    var color: String = "",
    var fuelType: String = "",
    var drivingForce: String = "",
    var yearOfManufacture: Int = 2000,
    var kilometer: Double = 0.0,
    var condition: String = "",
    var price: Double = 0.0,
    var description: String = "",
    var location: String = "",
    var timeOut: Boolean = false,
    var likesNumber: Int = -1,
    var isLiked: Boolean = false,
    var needRefresh: Boolean = false,
    var rateValue: Int = 0,
    var showRating: Boolean = false,
    //user Info :
    var userId: Int = -1,
    var userName: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var userImage: String? = null,
)
