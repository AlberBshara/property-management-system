package com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases

import android.content.Context
import com.example.pms.R


class ValidatePassword {

   companion object {
       fun run(
           password : String,
           context : Context
       ): ValidationResult {
           if (password.length in 1..7) {
               return ValidationResult(
                   success = false,
                   errorMessage = context.getString(R.string.password_less_than_eight)
               )
           }
           val containsLettersAndDigit = password.any { it.isDigit() }
                   && password.any { it.isLetter()}

           if (!containsLettersAndDigit) {
               return ValidationResult(
                   success = false,
                   errorMessage = context.getString(R.string.invalid_password)
               )
           }
           return ValidationResult(
               success = true
           )
       }
   }
}