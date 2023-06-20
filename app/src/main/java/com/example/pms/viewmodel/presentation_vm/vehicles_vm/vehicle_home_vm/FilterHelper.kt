package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm

import com.example.pms.model.FilteringData
import com.example.pms.viewmodel.api.util.Keys

class FilterHelper(
    private val FILTERING_TYPE : String
) {

     fun <ValueType : Any> filteringType(
        filteringWith: Pair<String, ValueType>
    ): FilteringData? =
        when (filteringWith.first) {
            Keys.OPERATION_TYPE -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    operationType = filteringWith.second.toString()
                )
            }
            Keys.TRANSMISSION_TYPE -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    transmissionType = filteringWith.second.toString()
                )
            }
            Keys.FUEL_TYPE -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    fuelType = filteringWith.second.toString()
                )
            }
            Keys.CONDITION -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    condition = filteringWith.second.toString()
                )
            }
            Keys.DRIVING_FORCE -> {
                FilteringData(
                    type =FILTERING_TYPE,
                    drivingForce = filteringWith.second.toString()
                )
            }
            Keys.BRAND -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    brand = filteringWith.second.toString()
                )
            }
            Keys.SECONDARY_BRAND -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    secondaryBrand = filteringWith.second.toString()
                )
            }
            Keys.COLOR -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    color = filteringWith.second.toString()
                )
            }
            Keys.LOCATION -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    location = filteringWith.second.toString()
                )
            }
            Keys.MIN_YEAR -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    minimumYear = filteringWith.second as Int
                )
            }
            Keys.MAX_YEAR -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    maximumYear = filteringWith.second as Int
                )
            }
            Keys.MIN_PRICE -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    minimumPrice = filteringWith.second as Double
                )
            }
            Keys.MAX_PRICE -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    maximumPrice = filteringWith.second as Double
                )
            }
            Keys.KILOMETERS -> {
                FilteringData(
                    type = FILTERING_TYPE,
                    maximumKilometers = filteringWith.second as Double
                )
            }
            else -> {
                null
            }
        }
}