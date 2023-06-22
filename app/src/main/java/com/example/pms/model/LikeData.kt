package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class LikeData(
    @SerializedName(Keys.ID)
    val postId: Int,
    @SerializedName(Keys.TYPE)
    val type: String
) {
    data class LikedResponse(
        @SerializedName(Keys.STATUS)
        val success: Boolean,
        @SerializedName(Keys.MESSAGE)
        val message : String
    )
}
