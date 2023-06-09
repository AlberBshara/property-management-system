package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class ProfileData(
    @SerializedName(Keys.USER)
    val user: User
) {
    data class User(
        @SerializedName(Keys.ID)
        val id: Int,
        @SerializedName(Keys.NAME)
        val name: String,
        @SerializedName(Keys.EMAIL)
        val email: String,
        @SerializedName("email_verified_at")
        val lastVerificationOfEmail: String?,
        @SerializedName(Keys.PHONE_NUMBER)
        val phoneNumber: String,
        @SerializedName(Keys.IMAGE)
        val profileImage: String?,
        @SerializedName(Keys.ROLE)
        val role: String,
        @SerializedName(Keys.FACEBOOK_LINK)
        val facebookLink: String?,
        @SerializedName(Keys.INSTAGRAM_LINK)
        val instagramLink: String?,
        @SerializedName(Keys.TWITTER_LINK)
        val twitterLink: String?,
        @SerializedName(Keys.CREATION_DATE)
        val createdAt: String,
        @SerializedName(Keys.UPDATED_DATE)
        val updatedDate: String,
    )
}
