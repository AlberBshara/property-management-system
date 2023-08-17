package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page4

import android.content.Context
import androidx.navigation.NavHostController

sealed class RegisterPage4events {


    data class OnCodeChange(val code: String) : RegisterPage4events()

    data class OnDone(
        val code: String,
        val context: Context,
        val navController: NavHostController
    ) : RegisterPage4events()

}
