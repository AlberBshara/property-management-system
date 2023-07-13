package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class AllChattedResponse(
    @SerializedName("status")
    val success: Boolean,
    @SerializedName("Chatted_Person :")
    val chattedList: List<ChattedPerson>
) {
    data class ChattedPerson(
        @SerializedName(Keys.ID)
        val id: Int,
        @SerializedName(Keys.NAME)
        val userName: String,
        @SerializedName(Keys.EMAIL)
        val email: String,
        @SerializedName(Keys.PHONE_NUMBER)
        val phoneNumber: String,
        @SerializedName(Keys.IMAGE)
        val imageUrl: String?
    ) {
        fun toChattingListItem(): ChattingItemData =
            ChattingItemData(
                id = this.id,
                username = this.userName,
                phoneNumber = this.phoneNumber,
                imageUrl = this.imageUrl
            )
    }
}
