package com.example.pms.viewmodel.presentation_vm.estates_vm.view_more_estate_vm

import com.example.pms.R

class RoomsHomePost(garage: Int, bed: Int, bath: Int, level: Int) {
    data class OneRoom(
        val image: Int,
        val number: Int,
        val name: String
    )

    val listOfRooms: List<OneRoom> = listOf(
        OneRoom(image = R.drawable.bed_icon, number = bed, name = "Beds"),
        OneRoom(image = R.drawable.bathroom_icon, number = bath, name = "Baths"),
        OneRoom(image = R.drawable.garage_icon, number = garage, name = "Garage"),
        OneRoom(image = R.drawable.level_icon, number = level, name = "Levels")
    )
}

data class RoomsViewMore(val state: ViewMoreEstateStates) {
    data class OneRoom(
        val image: Int,
        val number: Int,
        val name: String
    )

    val listOfRooms: List<OneRoom> = listOf(
        OneRoom(image = R.drawable.bed_icon, number = state.beds, name = "Beds"),
        OneRoom(image = R.drawable.bathroom_icon, number = state.baths, name = "Baths"),
        OneRoom(image = R.drawable.garage_icon, number = state.garage, name = "Garage"),
        OneRoom(image = R.drawable.level_icon, number = state.levels, name = "Levels")
    )
}