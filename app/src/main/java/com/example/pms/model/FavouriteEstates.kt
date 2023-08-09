package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

object FavouriteEstates {


    data class GetNumberOfLikesRequest(
        @SerializedName(Keys.ID)
        val id: Int,
        @SerializedName(Keys.TYPE)
        val type: String
    )

    data class GetNumberOfLikesResponse(
        val Likes_number: Int
    )

    data class AddOrRemoveFromFavouritesRequest(
        @SerializedName(Keys.ID)
        val id: Int,
        @SerializedName(Keys.TYPE)
        val type: String
    )

    data class AddOrRemoveFromFavouritesResponse(
        @SerializedName(Keys.STATUS)
        val status: Boolean,
        @SerializedName(Keys.MESSAGE)
        val message: String
    )

}