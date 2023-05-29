package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.google.gson.annotations.SerializedName
import java.io.File


data class RegisterUserData(
    @SerializedName(Keys.ID)
    val user_id : Int = -1 ,
    @SerializedName(Keys.NAME)
    val name: String,
    @SerializedName(Keys.EMAIL)
    val email: String,
    @SerializedName(Keys.PASSWORD)
    val password: String,
    @SerializedName(Keys.PHONE_NUMBER)
    val phone_number: String,
    @SerializedName(Keys.IMAGE)
    val image : File? = null
) {
     data class RegisterResponse (
         @SerializedName(Keys.STATUS)
         val status : Boolean,
         @SerializedName(Keys.USER)
         val user : RegisterUserData,
         @SerializedName(Keys.TOKEN)
         val token : String,
         @SerializedName(Keys.MESSAGE)
         val errorMessage : String? = null,
             )
}


