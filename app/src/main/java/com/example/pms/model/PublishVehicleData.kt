package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class PublishVehicleData(

    @SerializedName(Keys.OWNER_ID)
    val id : Int,
    @SerializedName(Keys.NAME)
    val name : String ,
    @SerializedName(Keys.OPERATION_TYPE)
    val operationType : String ,
    @SerializedName(Keys.TRANSMISSION_TYPE)
    val transmissionType : String ,
    @SerializedName(Keys.BRAND)
    val brand : String,
    @SerializedName(Keys.SECONDARY_BRAND)
    val secondaryBrand : String ,
    @SerializedName(Keys.LOCATION)
    val location : String ,
    @SerializedName(Keys.SECOND_LOCATION)
    val secondaryLocation: String ,
    @SerializedName(Keys.COLOR)
    val color : String,
    @SerializedName(Keys.DESCRIPTION)
    val description : String ,
    @SerializedName(Keys.PRICE)
    val price : String ,
    @SerializedName(Keys.YEAR)
    val year : String ,
    @SerializedName(Keys.KILOMETERS)
    val kilometers : String ,
    @SerializedName(Keys.IMAGE)
    val imagesFile : MultipartBody.Part? = null
)
