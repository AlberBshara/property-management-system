package com.example.pms.model

import com.google.gson.annotations.SerializedName

data class SendMessageResponse(
    @SerializedName("Chat : ")
    val chatResponse: String
) {
    data class Chat(
        @SerializedName("sender_id")
        val senderId: Int,
        @SerializedName("receiver_id")
        val receiverId: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("updated_at")
        val updatedAt: String
    )
}
