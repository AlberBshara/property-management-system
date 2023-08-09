package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class EstatePublishNewPostRequest(
    @SerializedName(Keys.OPERATION_TYPE)
    val operation_type: String,
    @SerializedName(Keys.GOVERNORATE)
    val governorate: String,
    @SerializedName(Keys.DESCRIPTION)
    val description: String,
    @SerializedName(Keys.PRICE)
    val price: String,
    @SerializedName(Keys.ADDRESS)
    val address: String,
    @SerializedName(Keys.ESTATE_STATUS)
    val status: String,
    @SerializedName(Keys.ESTATE_TYPE)
    val estate_type: String,
    @SerializedName(Keys.SPACE)
    val space: String,
    @SerializedName(Keys.BEDS)
    val beds: Int,
    @SerializedName(Keys.LEVEL)
    val level: Int,
    @SerializedName(Keys.BATHS)
    val baths: Int,
    @SerializedName(Keys.GARAGE)
    val garage: Int,
    @SerializedName(Keys.SECOND_LOCATION)
    val locationInDamascus: String,
) {
    data class EstatePublishNewPostResponse(
        @SerializedName(Keys.STATUS)
        val status: Boolean,
        @SerializedName(Keys.ESTATE2)
        val estate: EstatePublishNewPostRequest,
        @SerializedName(Keys.IMAGES_LIST)
        val imageList: List<EstateViewMoreData.ListOfImages>
    )
}
