package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page4

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.destinations.RegisterPages
import com.example.pms.viewmodel.preferences.SharedPreferencesUsage
import com.example.pms.viewmodel.preferences.UserPreferences
import com.example.pms.viewmodel.presentation_vm.register_vm.RegisterData
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch
import com.example.pms.R

class RegisterPage4Vm(
    private val userApiRepo: UserServicesRepository = UserServicesRepository()
) : ViewModel() {

    var state by mutableStateOf(RegisterPage4state())

    fun onEvent(event: RegisterPage4events) {

        when (event) {
            is RegisterPage4events.OnCodeChange -> {
                state = state.copy(code = event.code)
            }
            is RegisterPage4events.OnDone -> {
                verifyCheckCode(code = event.code, context = event.context, event.navController)
            }
        }
    }


    private fun verifyCheckCode(
        code: String, context: Context,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            val email = SharedPreferencesUsage(context).getEmail()
            Log.d("Page 4", "verifyCheckCode: $email")
            val response = userApiRepo.verifyEmailCheckCode(email, code)
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = it.isLoading)
                    }
                    is Resource.Success -> {
                        if (it.data?.status == true) {
                            TokenManager.getInstance(context).completed(false)
                            TokenManager.getInstance(context).verified(true)
                            TokenManager.getInstance(context).save(it.data.token)
                            UserPreferences.saveUserData(
                                UserPreferences.UserDataPreference(
                                    id =  it.data.user.id,
                                    email = it.data.user.email
                                ), context
                            )
                            RegisterPages.moveToNextRegisterPage(
                                navController,
                                RegisterPages.registerPage2,
                                RegisterData()
                            )
                            Log.d("Page4", "verifyCheckCode: ${it.data.token}")
                        } else {
                            Toast.makeText(
                                context, context.getString(R.string.enter_valid_code),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }
}