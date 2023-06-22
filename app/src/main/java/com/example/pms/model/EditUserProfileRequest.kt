package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName


data class EditUserProfileRequest(
    @SerializedName(Keys.NAME)
    val name: String,
    @SerializedName(Keys.PHONE_NUMBER)
    val phoneNumber: String
)

data class EditUserProfileResponse(
    @SerializedName(Keys.STATUS)
    val status: Boolean,
    @SerializedName(Keys.MESSAGE)
    val message: String
)

data class EditUserImageResponse(
    @SerializedName(Keys.STATUS)
    val status: Boolean,
    @SerializedName(Keys.MESSAGE)
    val message: String
)

