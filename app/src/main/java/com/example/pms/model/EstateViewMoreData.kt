package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class EstateViewMoreData(
    @SerializedName("Status")
    val status: Boolean,
    @SerializedName("Estate")
    val estate: EstateDetails,
    @SerializedName(Keys.IMAGES_LIST)
    val images: List<ListOfImages>,
    @SerializedName(Keys.OWNER)
    val owner: OwnerDetails,
    @SerializedName(Keys.FAVOURITE)
    val favourite: Boolean
) {
    data class EstateDetails(
        @SerializedName(Keys.ESTATE_TYPE)
        val estate_type: String,
        @SerializedName(Keys.OPERATION_TYPE)
        val operation_type: String,
        @SerializedName(Keys.ESTATE_STATUS)
        val status: String,
        @SerializedName(Keys.GOVERNORATE)
        val governorate: String,
        @SerializedName(Keys.ADDRESS)
        val address: String,
        @SerializedName(Keys.DESCRIPTION)
        val description: String,
        @SerializedName(Keys.PRICE)
        val price: String,
        @SerializedName(Keys.SPACE)
        val space: Float,
        @SerializedName(Keys.BEDS)
        val beds: String,
        @SerializedName(Keys.BATHS)
        val baths: String,
        @SerializedName(Keys.GARAGE)
        val garage: String,
        @SerializedName(Keys.LEVEL)
        val level: String,
    )

    data class ListOfImages(
        @SerializedName(Keys.NAME)
        val imageUrl: String,
    )


    data class OwnerDetails(
        @SerializedName(Keys.NAME)
        val name: String,
        @SerializedName(Keys.PHONE_NUMBER)
        val phone_number: String,
    )

}



