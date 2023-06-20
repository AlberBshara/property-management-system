package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class SearchData(
    @SerializedName(Keys.TYPE)
    val type: String,
    @SerializedName(Keys.DESCRIPTION)
    val query: String,
) {
    data class SearchResponse(
        @SerializedName(Keys.STATUS)
        val success: Boolean,
        @SerializedName("Posts")
        val vehiclesList : List<HomeVehiclesResponse.PostData>
        )
}
