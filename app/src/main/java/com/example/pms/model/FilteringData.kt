package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class FilteringData(
    @SerializedName(Keys.TYPE)
    val type: String,
    @SerializedName(Keys.OPERATION_TYPE)
    val operationType: String? = null,
    @SerializedName(Keys.TRANSMISSION_TYPE)
    val transmissionType: String? = null,
    @SerializedName(Keys.FUEL_TYPE)
    val fuelType: String? = null,
    @SerializedName(Keys.CONDITION)
    val condition: String? = null,
    @SerializedName(Keys.DRIVING_FORCE)
    val drivingForce: String? = null,
    @SerializedName(Keys.BRAND)
    val brand: String? = null,
    @SerializedName(Keys.SECONDARY_BRAND)
    val secondaryBrand: String? = null,
    @SerializedName(Keys.COLOR)
    val color: String? = null,
    @SerializedName(Keys.ADDRESS)
    val location: String? = null,
    @SerializedName(Keys.MIN_YEAR)
    val minimumYear: Int? = null,
    @SerializedName(Keys.MAX_YEAR)
    val maximumYear: Int? = null,
    @SerializedName(Keys.MIN_PRICE)
    val minimumPrice: Double? = null,
    @SerializedName(Keys.MAX_PRICE)
    val maximumPrice: Double? = null,
    @SerializedName(Keys.MAX_KILOMETERS)
    val maximumKilometers: Double? = null
) {
    data class FilteringResponse(
        @SerializedName(Keys.STATUS)
        val success: Boolean,
        @SerializedName("Posts")
        val vehiclesList : List<HomeVehiclesResponse.PostData>
    )
}
