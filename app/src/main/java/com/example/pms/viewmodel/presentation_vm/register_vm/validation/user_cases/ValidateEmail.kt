package com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases

import android.content.Context
import com.example.pms.R

class ValidateEmail {
    companion object {
        fun run(
            email: String,
            context: Context
        ): ValidationResult {
            if (email.isBlank()) {
                return ValidationResult(
                    success = false,
                    errorMessage = context.getString(R.string.email_blank)
                )
            }
            if (!isEmailValid(email)) {
                return ValidationResult(
                    success = false,
                    errorMessage = context.getString(R.string.unvalid_email)
                )
            }
            return ValidationResult(
                success = true
            )
        }
        private fun isEmailValid(email: String): Boolean {
            val emailPattern = "[a-zA-Z0-9._-]+@gmail\\.com"
            return email.matches(emailPattern.toRegex())
        }
    }
}