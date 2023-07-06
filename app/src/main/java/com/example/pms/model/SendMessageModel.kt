package com.example.pms.model

import com.google.gson.annotations.SerializedName

data class SendMessageModel(
    @SerializedName("receiver_id")
    val receiverId: Int,
    @SerializedName("message")
    val message: String
)
