package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm

import android.content.Context
import com.example.pms.viewmodel.api.util.Keys
import com.example.pms.R

class VehicleHomeConstants(
    context: Context
) {

    companion object {
        enum class FilteringTitles(val key: String) {
            All("All"),
            Operation(Keys.OPERATION_TYPE),
            Transmission(Keys.TRANSMISSION_TYPE),
            Fuel(Keys.FUEL_TYPE),
            Driving(Keys.DRIVING_FORCE),
            Brand(Keys.BRAND),
            Color(Keys.COLOR),
            Status(Keys.CONDITION),
            Price(Keys.MAX_PRICE),
        }
    }


    val listOfFiltering = listOf(
        FilteringType(title = FilteringTitles.All, items = listOf("All")),
        FilteringType(title = FilteringTitles.Operation, items = listOf("sell", "rent")),
        FilteringType(
            title = FilteringTitles.Transmission,
            items = listOf("automatic", "manually")
        ),
        FilteringType(
            title = FilteringTitles.Fuel,
            items = listOf("gasoline", "hybrid", "diesel", "electric")
        ),
        FilteringType(title = FilteringTitles.Driving, items = listOf("FWD", "4WD", "RWD")),
        FilteringType(
            title = FilteringTitles.Brand,
            items = listOf(*context.resources.getStringArray(R.array.car_brands))
        ),
        FilteringType(
            title = FilteringTitles.Color,
            items = listOf(*context.resources.getStringArray(R.array.colors_list))
        ),
        FilteringType(title = FilteringTitles.Status, items = listOf("new", "used")),
        FilteringType(title = FilteringTitles.Price , items = listOf())
    )

    data class FilteringType(
        val title: FilteringTitles,
        val items: List<String>
    )


}