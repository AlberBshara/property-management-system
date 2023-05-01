package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page3

import android.content.Context
import androidx.navigation.NavHostController

sealed class RegPage3Events {
    data class Submit(
        val navController: NavHostController,
        val context: Context
    ) : RegPage3Events()

    sealed class WifiCase() : RegPage3Events() {
        data class Confirm(
            val navController: NavHostController,
            val context: Context
        ) : WifiCase()

        object Deny : WifiCase()
    }

    data class DuplicatedEmailDone(
        val navController: NavHostController
        ) : RegPage3Events()
}
