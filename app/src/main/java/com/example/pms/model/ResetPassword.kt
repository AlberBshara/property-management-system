package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

object ResetPassword {

    data class SendEmailRequest(
        @SerializedName(Keys.EMAIL)
        val email: String
    )

    data class SendEmailResponse(

        @SerializedName(Keys.STATUS)
        var status: Boolean,
        @SerializedName(Keys.MESSAGE)
        var message: String? = null,
        @SerializedName(Keys.ERROR_MESSAGE)
        val errorMessage: ErrorMessage? = null
    ) {
        data class ErrorMessage(
            @SerializedName(Keys.EMAIL)
            val email: List<String>
        )
    }

    data class SendCodeRequest(
        @SerializedName(Keys.CODE)
        var code: String
    )

    data class SendCodeResponse(
        @SerializedName(Keys.STATUS)
        var status: Boolean,
        @SerializedName(Keys.MESSAGE)
        var message: String? = null,
        @SerializedName(Keys.ERROR_MESSAGE)
        val errorMessage: ErrorMessage? = null
    ) {
        data class ErrorMessage(
            @SerializedName(Keys.CODE)
            val code: List<String>
        )
    }

    data class SendResetRequest(
        @SerializedName(Keys.CODE)
        val code: String,
        @SerializedName(Keys.PASSWORD)
        val password: String,
        @SerializedName(Keys.PASSWORD_CONFIRMATION)
        val password_confirmation: String
    )

    data class SendResetResponse(
        @SerializedName(Keys.STATUS)
        val status: Boolean,
        @SerializedName(Keys.MESSAGE)
        val message: String? = null,
        @SerializedName(Keys.ERROR_MESSAGE)
        val errorMessage: ErrorMessage? = null
    ) {
        data class ErrorMessage(
            @SerializedName(Keys.PASSWORD)
            val password: List<String>,
            @SerializedName(Keys.CODE)
            val code: List<String>
        )
    }
}