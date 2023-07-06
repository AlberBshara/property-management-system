package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

object MyPostsModels {

    data class MyVehiclesPostResponse(
        @SerializedName(Keys.STATUS)
        val success: Boolean,
        @SerializedName("Cars")
        val vehiclesPostsList: List<MyFavResponse.PostData>
    ) {
        data class MyVehiclesPostModel(
            @SerializedName(Keys.TYPE)
            val type: String = "car"
        )
    }

    data class MyEstatesPostResponse(
        @SerializedName(Keys.STATUS)
        val success: Boolean,
        @SerializedName("Estates")
        val estatesPostsList: List<MyFavResponse.PostData>
    ) {
        data class MyEstatesPostModel(
            @SerializedName(Keys.TYPE)
            val type: String = "estate"
        )
    }
}


