package com.example.pms.viewmodel.presentation_vm.login_vm.validation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

open class TextFieldState(
    private var validator: (String) -> Boolean = { true },
    private var errorMessage: (String) -> String
) {
    var error by mutableStateOf<String?>(null)

    fun validate(
        text: String
    ) {
        error =
            if (validator(text))
                null
            else
                errorMessage(text)

    }

}