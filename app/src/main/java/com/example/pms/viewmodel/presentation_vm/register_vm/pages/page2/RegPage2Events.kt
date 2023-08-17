package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page2

import android.content.Context
import androidx.navigation.NavHostController


sealed class RegPage2Events {

    data class EmailChanged(
        val email: String
    ) : RegPage2Events()

    data class PasswordChanged(
        val password: String
    ) : RegPage2Events()

    data class RepeatedPasswordChanged(
        val repeatedPassword: String
    ) : RegPage2Events()

    data class OnSubmitClicked(
        val navController : NavHostController,
        val context: Context
    ) : RegPage2Events()

    data class OnDuplicatedEmailClicked(
        val navController: NavHostController
    ) : RegPage2Events()

}
