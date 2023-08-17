package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

object RegisterWithVerification {

    data class RegisterEmail(
        @SerializedName(Keys.EMAIL)
        val email: String,
        @SerializedName(Keys.PASSWORD)
        val password: String
    )

}