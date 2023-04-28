package com.example.pms.viewmodel.presentation_vm.login_vm.validation

import android.annotation.SuppressLint
import android.content.Context
import com.example.pms.R
import java.util.regex.Pattern

class PasswordState(
    private val context : Context
) : TextFieldState(
    validator = { password -> isPasswordValid(password) },
    errorMessage = { password -> passwordErrorMessage(password) }
){
    init {
        ctxt = this.context
    }
}


@SuppressLint("StaticFieldLeak")
private lateinit var ctxt : Context
@SuppressLint("StaticFieldLeak")
private val Password_Validation_Regex = "(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}"

private fun isPasswordValid(password: String): Boolean =
    Pattern.matches(Password_Validation_Regex, password)


private fun passwordErrorMessage(password: String): String {

    if (!isPasswordValid(password = password)) {

        if (!password.any { it.isDigit() }) {
            return ctxt.getString(R.string.invalid_password)
        }
        if (!password.any { it.isLetter() }) {
            return ctxt.getString(R.string.password_letters_problem)
        }
        if (password.length < 8) {
            return ctxt.getString(R.string.password_less_than_eight)
        }

        if (password.contains(' ')) {
            return ctxt.getString(R.string.password_contains_spaces)
        }
    }
    return ""
}
