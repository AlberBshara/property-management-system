package com.example.pms.model

data class ChattingItemData(
    val id : Int  ,
    val imageUrl : String? = null ,
    val username : String ,
    val lastMessage : String? = null,
    val phoneNumber : String
)
