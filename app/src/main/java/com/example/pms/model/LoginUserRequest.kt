package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class LoginUserRequest(
    @SerializedName(Keys.EMAIL)
    val email: String,
    @SerializedName(Keys.PASSWORD)
    val password: String
) {

    data class LoginResponse(
        @SerializedName(Keys.STATUS)
        var status: Boolean,
        @SerializedName(Keys.USER)
        var user: LoginUserRequest,
        @SerializedName(Keys.TOKEN)
        var token: String,
        @SerializedName(Keys.MESSAGE)
        val errorMessage: String? = null

    )
}
