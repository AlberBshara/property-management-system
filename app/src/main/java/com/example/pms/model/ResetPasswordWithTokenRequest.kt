package com.example.pms.model


data class ResetPasswordWithTokenRequest(
    val old_password: String,
    val new_password: String
) {
    data class ResetPasswordWithTokenResponse(
        val Status: Boolean,
        val message: String,
        val error: String
    )
}
