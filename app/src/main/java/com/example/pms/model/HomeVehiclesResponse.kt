package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class HomeVehiclesResponse(
    @SerializedName(Keys.ALL_POSTS)
    val allPosts: AllPosts
) {

    data class AllPosts(
        @SerializedName(Keys.CURRENT_PAGE)
        val currentPage: Int,
        @SerializedName(Keys.DATA)
        val postsData: List<PostData>,
        @SerializedName(Keys.LAST_PAGE)
        val lastPage : Int ,
    )


    data class PostData(
        @SerializedName(Keys.POST)
        val vehicleData: VehicleData,
        @SerializedName("images")
        val images: List<VehicleViewMoreData.ImageData>,
        @SerializedName("favorite")
        val liked : Boolean
    )

    data class VehicleData(
        @SerializedName(Keys.ID)
        val id: Int,
        @SerializedName(Keys.OWNER_ID)
        val ownerId: Int,
        @SerializedName(Keys.OPERATION_TYPE)
        val operationType: String,
        @SerializedName(Keys.TRANSMISSION_TYPE)
        val transmissionType: String,
        @SerializedName(Keys.DRIVING_FORCE)
        val drivingForce: String,
        @SerializedName(Keys.FUEL_TYPE)
        val fuelType: String,
        @SerializedName(Keys.CONDITION)
        val status: String,
        @SerializedName(Keys.BRAND)
        val brand: String,
        @SerializedName(Keys.SECONDARY_BRAND)
        val secondaryBrand: String?,
        @SerializedName(Keys.GOVERNORATE)
        val governorate: String,
        @SerializedName(Keys.SECOND_LOCATION)
        val locationInDamascus: String,
        @SerializedName(Keys.ADDRESS)
        val address: String?,
        @SerializedName(Keys.COLOR)
        val color: String?,
        @SerializedName(Keys.DESCRIPTION)
        val description: String,
        @SerializedName(Keys.PRICE)
        val price: Double,
        @SerializedName(Keys.YEAR)
        val yearOfManufacture: Int,
        @SerializedName(Keys.KILOMETERS)
        val kilometers: Double,
        @SerializedName(Keys.CREATION_DATE)
        val createdAt: String,
        @SerializedName(Keys.UPDATED_DATE)
        val updatedAt: String
    )
}
