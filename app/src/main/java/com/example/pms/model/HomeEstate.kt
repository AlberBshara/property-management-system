package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class GetAllEstateResponse(
    @SerializedName("AllPost")
    val allPosts: AllPosts
) {

    data class AllPosts(
        @SerializedName(Keys.CURRENT_PAGE)
        val currentPage: Int,
        @SerializedName(Keys.DATA)
        val postsData: List<PostData>,
        @SerializedName(Keys.LAST_PAGE)
        val lastPage: Int,
    )


    data class PostData(
        @SerializedName(Keys.POST)
        val estateData: EstateData,
        @SerializedName("images")
        val images: List<EstateViewMoreData.ListOfImages>,
        @SerializedName(Keys.FAVOURITE)
        var favourite: Boolean
    )

    data class EstateData(
        @SerializedName(Keys.ID)
        val estate_id: Int,
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
}


