package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class AddVehicleRateData(
    @SerializedName(Keys.TYPE)
    val type : String ,
    @SerializedName(Keys.CAR_ID)
    val carId : Int ,
    @SerializedName(Keys.RATE)
    val rateValue : Int
) {
    data class RatingVehicleResponse(
        @SerializedName("status")
        val success : Boolean ,
        @SerializedName(Keys.MESSAGE)
        val message : String
    )
}
