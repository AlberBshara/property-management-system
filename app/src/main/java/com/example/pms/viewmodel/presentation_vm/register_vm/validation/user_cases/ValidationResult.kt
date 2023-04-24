package com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases

data class ValidationResult(
    val success : Boolean,
    val errorMessage : String? = null
)
