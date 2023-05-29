package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_details_vm
import com.example.pms.R

data class VehicleDetailsState(
    var imagesList: List<Int> = listOf(
        R.drawable.car,
        R.drawable.car,
        R.drawable.car,
        R.drawable.car,
        R.drawable.car
    ),
    var currentImageIndex : Int = 0,
)
