package com.example.pms.viewmodel.presentation_vm.login_vm.validation


import android.annotation.SuppressLint
import android.content.Context
import com.example.pms.R
import java.util.regex.Pattern

class EmailState(
    private val context: Context
) : TextFieldState(

    validator = { email -> isEmailValid(email) },
    errorMessage = { email -> emailErrorMessage(email) }

) {
    init {
        ctxt = this.context
    }
}


@SuppressLint("StaticFieldLeak")
private lateinit var ctxt: Context
@SuppressLint("StaticFieldLeak")
private val Email_Validation_Regex = "^(.+)@(.+)\$"


private fun isEmailValid(email: String): Boolean {
    return Pattern.matches(Email_Validation_Regex, email)
}

private fun emailErrorMessage(email: String) = "${ctxt.getString(R.string.email)} $email" +
        ", ${ctxt.getString(R.string.invalid)}"






