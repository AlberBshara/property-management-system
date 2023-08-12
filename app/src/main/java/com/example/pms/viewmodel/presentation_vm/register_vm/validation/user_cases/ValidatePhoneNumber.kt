package com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases

import android.content.Context
import com.example.pms.R

class ValidatePhoneNumber {

    companion object {
        fun run(
            phoneNumber: String,
            countryCode: String,
            context: Context
        ): ValidationResult {
            if (phoneNumber.isBlank()) {
                return ValidationResult(
                    success = false,
                    errorMessage = context.getString(R.string.phoneNumber_blank)
                )
            }
            if (invalidPhoneNumber(phoneNumber)) {
                return ValidationResult(
                    success = false,
                    errorMessage = context.getString(R.string.invalid_phone_number)
                )
            }
            if (lessThanEight(phoneNumber)){
                return ValidationResult(
                    success = false,
                    errorMessage = context.getString(R.string.invalid_phone_number)
                )
            }
            if (countryCode.isBlank()) {
                return ValidationResult(
                    success = false,
                    errorMessage = context.getString(R.string.country_code_needed)
                )
            }
            return ValidationResult(
                success = true
            )
        }

        private fun invalidPhoneNumber(phoneNumber: String): Boolean = phoneNumber.any {
            it.isLetter()
        }
        private fun lessThanEight(phoneNumber: String) : Boolean =
            phoneNumber.length < 8
    }
}