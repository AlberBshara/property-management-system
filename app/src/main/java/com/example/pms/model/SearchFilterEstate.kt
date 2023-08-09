package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

object SearchFilterEstate {

    data class SearchEstateRequest(
        @SerializedName(Keys.TYPE)
        val type: String,
        @SerializedName(Keys.DESCRIPTION)
        val description: String
    )

    data class SearchEstateResponse(
        @SerializedName(Keys.STATUS)
        val status: Boolean,
        val Posts: List<GetAllEstateResponse.PostData>
    )

    data class FilterEstateRequest(
        @SerializedName(Keys.TYPE)
        val type: String,
        @SerializedName(Keys.ESTATE_TYPE)
        val estateType: String?,
        @SerializedName(Keys.OPERATION_TYPE)
        val operationType: String?,
        @SerializedName(Keys.GOVERNORATE)
        val governorate: String?,
        @SerializedName(Keys.ESTATE_STATUS)
        val status: String?,
        val min_price: Int?,
        val max_price: Int?,
        val min_space: Int?,
        val max_space: Int?,
        val min_level: Int?,
        val max_level: Int?,

        )

    data class FilterEstateResponse(
        @SerializedName(Keys.STATUS)
        val status: Boolean,
        val Posts: List<GetAllEstateResponse.PostData>
    )


}