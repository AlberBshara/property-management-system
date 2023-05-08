package com.example.pms.viewmodel.presentation_vm.vehicles_vm.postcard_vehicle_vm

import com.example.pms.R


data class PostVehicleState(
    var loved : Boolean = false,
    var carName : String = "2021 Toyota Corolla",
    var carPrice : String = "$20,000",
    var previousPrice : String =  "$20,500" ,
    var images: List<Int> = listOf(
        R.drawable.car,
        R.drawable.car,
        R.drawable.car
    ),
    var currentImageIndex : Int = 0,
    val usedVehicle : Boolean = false
)