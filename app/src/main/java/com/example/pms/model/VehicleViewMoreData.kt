package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName

data class VehicleViewMoreData(
    @SerializedName(Keys.STATUS)
    val status : Boolean,
    @SerializedName("Car : ")
    val vehicle : VehicleData,
    @SerializedName("Images : ")
    val imagesList : List<ImageData>
) {
    data class VehicleData(
        @SerializedName(Keys.ID)
        val id : Int,
        @SerializedName(Keys.OWNER_ID)
        val ownerId : Int ,
        @SerializedName(Keys.OPERATION_TYPE)
        val operationType : String ,
        @SerializedName(Keys.TRANSMISSION_TYPE)
        val transmissionType : String ,
        @SerializedName(Keys.DRIVING_FORCE)
        val drivingForce : String,
        @SerializedName(Keys.FUEL_TYPE)
        val fuelType : String ,
        @SerializedName(Keys.CONDITION)
        val condition : String ,
        @SerializedName(Keys.BRAND)
        val brand : String ,
        @SerializedName(Keys.SECONDARY_BRAND)
        val secondaryBrand : String ?,
        @SerializedName(Keys.GOVERNORATE)
        val governorate : String? ,
        @SerializedName(Keys.SECOND_LOCATION)
        val locationInDamascus : String ,
        @SerializedName(Keys.ADDRESS)
        val address : String? ,
        @SerializedName(Keys.COLOR)
        val color : String? ,
        @SerializedName(Keys.DESCRIPTION)
        val description : String ,
        @SerializedName(Keys.PRICE)
        val price : Double ,
        @SerializedName(Keys.YEAR)
        val yearOfManufacture : Int ,
        @SerializedName(Keys.KILOMETERS)
        val kilometers : Double ,
        @SerializedName(Keys.CREATION_DATE)
        val publishingDate : String,
        @SerializedName(Keys.UPDATED_DATE)
        val updatedDate : String
    )
    data class ImageData(
        @SerializedName(Keys.ID)
        val id : Int ,
        @SerializedName(Keys.NAME)
        val imageUrl : String ,
        @SerializedName(Keys.PROPERTY_TYPE)
        val imageLabel : String ,
        @SerializedName(Keys.CAR_ID)
        val carId : Int ,
        @SerializedName(Keys.ESTATE_ID)
        val estateId : Int ? ,
        @SerializedName(Keys.CREATION_DATE)
        val creationDate : String ,
        @SerializedName(Keys.UPDATED_DATE)
        val updatedDate : String
    )
}
