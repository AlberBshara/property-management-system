package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class RateVehicleData(
    @SerializedName(Keys.TYPE)
    val type: String,
    @SerializedName(Keys.CAR_ID)
    val carId: Int
) {
    data class RateVehicleResponse(
        @SerializedName(Keys.STATUS)
        val success: Boolean,
        @SerializedName(Keys.RATE)
        val rate: Int
    )
}


