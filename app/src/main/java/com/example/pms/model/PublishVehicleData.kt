package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class PublishVehicleData(
    @SerializedName(Keys.OPERATION_TYPE)
    val operationType : String,
    @SerializedName(Keys.TRANSMISSION_TYPE)
    val transmissionType : String,
    @SerializedName(Keys.BRAND)
    val brand : String,
    @SerializedName(Keys.SECONDARY_BRAND)
    val secondaryBrand : String,
    @SerializedName(Keys.GOVERNORATE)
    val governorate : String,
    @SerializedName(Keys.SECOND_LOCATION)
    val locationInDamascus : String,
    @SerializedName(Keys.COLOR)
    val color : String,
    @SerializedName(Keys.DESCRIPTION)
    val description : String,
    @SerializedName(Keys.PRICE)
    val price : Double,
    @SerializedName(Keys.YEAR)
    val yearOfManufacture : Int,
    @SerializedName(Keys.KILOMETERS)
    val kilometers : Double,
    @SerializedName(Keys.ADDRESS)
    val address : String,
    @SerializedName(Keys.FUEL_TYPE)
    val fuelType : String,
    @SerializedName(Keys.CONDITION)
    val condition : String,
    @SerializedName(Keys.DRIVING_FORCE)
    val drivingForce : String,
){

    data class PublishVehicleResponse(
      @SerializedName(Keys.STATUS)
      val status : Boolean ,
      @SerializedName(Keys.CAR)
      val vehicle : PublishVehicleData,
      @SerializedName(Keys.IMAGES_LIST)
      val imagesList : List<VehicleViewMoreData.ImageData>? = null
    )
}
