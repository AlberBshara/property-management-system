package com.example.pms.model

import com.google.gson.annotations.SerializedName

data class PreviousMessagesModel(
    @SerializedName("receiver_id")
    val receiverId : Int
)
