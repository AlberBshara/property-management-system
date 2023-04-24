package com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases

import android.annotation.SuppressLint
import android.content.Context
import com.example.pms.R


class ValidateConfirmPassword {

  companion object {
      fun run(
          password: String,
          repeatedPassword: String,
          context: Context
      ): ValidationResult {
          if (password != repeatedPassword) {
              return ValidationResult(
                  success = false,
                  errorMessage = context.getString(R.string.passwords_did_not_match)
              )
          }
          return ValidationResult(
              success = true
          )
      }

  }
}