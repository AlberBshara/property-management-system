package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class MyFavResponse(
    @SerializedName("favorite_cars : ")
    val favList: List<PostData>
) {
    data class PostData(
        @SerializedName(Keys.POST)
        val vehicleData: HomeVehiclesResponse.VehicleData,
        @SerializedName("images")
        val images: List<VehicleViewMoreData.ImageData>,
        @SerializedName("favorite")
        val liked: Boolean
    )

    data class MyFavModel(
        @SerializedName(Keys.TYPE)
        val type: String
    )
}


data class MyFavResponseEstate(
    @SerializedName("favorite_estates : ")
    val favList: List<PostData>
) {
    data class PostData(
        @SerializedName(Keys.POST)
        val estateData: GetAllEstateResponse.EstateData,
        @SerializedName("images")
        val images: List<EstateViewMoreData.ListOfImages>,
        @SerializedName("favorite")
        val liked: Boolean
    )
}