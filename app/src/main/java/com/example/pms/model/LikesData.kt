package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class LikesData(
    @SerializedName(Keys.ID)
    val id : Int ,
    @SerializedName(Keys.TYPE)
    val type : String
) {
    data class LikesNumResponse(
        @SerializedName("Likes_number")
        val likesNumber : Int
    )
}
