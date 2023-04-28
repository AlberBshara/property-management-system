package com.example.pms.model

import com.example.pms.viewmodel.api.util.Keys
import com.squareup.moshi.Json

data class RegisterUserData(
    @field:Json(name= Keys.NAME) val name : String,
    @field:Json(name= Keys.EMAIL ) val email : String,
    @field:Json(name= Keys.PASSWORD) val password : String,
    @field:Json(name= Keys.PHONE_NUMBER)val phone_number : String,
  //  val image : Int
)
