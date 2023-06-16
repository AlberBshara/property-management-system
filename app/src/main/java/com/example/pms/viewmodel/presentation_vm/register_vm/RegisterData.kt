package com.example.pms.viewmodel.presentation_vm.register_vm

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class RegisterData(
    var firstname : String = "",
    var lastname : String = "",
    var phoneNumber : String = "",
    var email : String = "",
    var password : String = "",
    var confirmPassword : String = "",
) : Parcelable
