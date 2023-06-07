package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName(Keys.STATUS)
    val isSuccess: Boolean,
    @SerializedName(Keys.MESSAGE)
    val message: String
)
