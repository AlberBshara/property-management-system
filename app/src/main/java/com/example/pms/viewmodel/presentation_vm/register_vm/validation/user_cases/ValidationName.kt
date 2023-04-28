package com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases

import android.content.Context
import com.example.pms.R

class ValidationName {
    companion object {
        fun run(
            name: String,
            context: Context
        ): ValidationResult {
            if (name.isBlank()) {
                return ValidationResult(
                    success = false,
                    errorMessage = context.getString(R.string.firstname_blank)
                )
            }
            if (!validName(name)) {
                return ValidationResult(
                    success = false,
                    errorMessage = context.getString(R.string.invalid_name)
                )
            }
            return ValidationResult(
                success = true
            )
        }

        private fun validName(string: String): Boolean {
            val regex = Regex("[\\u0600-\\u06FF\\u0750-\\u077F\\uFB50-\\uFDFF\\uFE70-\\uFEFFa-zA-Z]+")
            return string.matches(regex)
        }
    }
}