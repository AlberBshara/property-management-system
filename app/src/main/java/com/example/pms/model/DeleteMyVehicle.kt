package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class DeleteMyVehicle(
    @SerializedName(Keys.STATUS)
    val success: Boolean,
    @SerializedName(Keys.MESSAGE)
    val message: String
)
