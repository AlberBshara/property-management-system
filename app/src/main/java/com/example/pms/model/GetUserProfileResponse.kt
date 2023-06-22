package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class GetUserProfileResponse(
    @SerializedName(Keys.USER)
    val user: User
) {
    data class User(
        @SerializedName(Keys.NAME)
        val name: String,
        @SerializedName(Keys.PHONE_NUMBER)
        val phoneNumber: String,
        @SerializedName(Keys.IMAGE)
        val image: String
    )
}