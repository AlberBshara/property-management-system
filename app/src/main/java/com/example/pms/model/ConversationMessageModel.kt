package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName


data class ConversationMessageModel(
    @SerializedName(Keys.ID)
    val messageId: Int,
    @SerializedName("sender_id")
    val senderId : Int ,
    @SerializedName("receiver_id")
    val receiverId : Int ,
    @SerializedName("message")
    val message: String ,
//    @SerializedName(Keys.CREATION_DATE)
//    val time : String
)
