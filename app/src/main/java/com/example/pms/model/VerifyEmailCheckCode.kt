package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class VerifyEmailCheckCodeRequest(
    @SerializedName(Keys.EMAIL)
    val email:String,
    @SerializedName(Keys.CODE)
    val code:String
)

data class VerifyEmailCheckCodeResponse(
    @SerializedName(Keys.STATUS)
    val status:Boolean,
    @SerializedName(Keys.USER)
    val user:User,
    @SerializedName("token")
    val token:String
){
    data class User(
        @SerializedName(Keys.ID)
        val id: Int,
        @SerializedName(Keys.EMAIL)
        val email: String
    )
}

data class SendCodeToGmailRequest(
    @SerializedName(Keys.EMAIL)
    val email:String
)

data class SendCodeTOGmailTResponse(
    @SerializedName(Keys.STATUS)
    val status:Boolean
)