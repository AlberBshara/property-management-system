package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

object RateEstate {

    data class GetRateEstateRequest(
        @SerializedName(Keys.TYPE)
        val type: String,
        @SerializedName("estate_id")
        val estateId: Int,
    )

    data class GetRateEstateResponse(
        @SerializedName(Keys.STATUS)
        val status: Boolean,
        @SerializedName(Keys.RATE)
        val rate: Float
    )

    data class AddRateEstateRequest(
        @SerializedName(Keys.TYPE)
        val type: String,
        val estate_id: Int,
        @SerializedName(Keys.RATE)
        val rate: Int
    )


    data class AddRateEstateResponse(
        val status: Boolean
    )


}