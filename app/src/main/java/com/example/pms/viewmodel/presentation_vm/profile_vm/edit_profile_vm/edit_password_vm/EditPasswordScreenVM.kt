package com.example.pms.viewmodel.presentation_vm.profile_vm.edit_profile_vm.edit_password_vm


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pms.R
import com.example.pms.model.ResetPasswordWithTokenRequest
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.presentation_vm.login_vm.validation.PasswordState
import com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases.ValidateConfirmPassword
import com.example.pms.viewmodel.utils.InternetConnection
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch

class EditPasswordScreenVM(
    private val userApiRepo: UserServicesRepository = UserServicesRepository()
) : ViewModel() {


    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    fun setContext(context: Context) {
        this.context = context
    }

    companion object {
        private const val TAG: String = "EditPasswordScreenVM.kt"
    }

    var state by mutableStateOf(EditPasswordStates())
    val passwordState by lazy { mutableStateOf(PasswordState(context)) }


    @RequiresApi(Build.VERSION_CODES.M)
    fun onEvent(event: EditPasswordEvents) {
        when (event) {
            is EditPasswordEvents.ChangeOldPassword -> {
                state = state.copy(oldPassword = event.oldPassword)
            }
            is EditPasswordEvents.PasswordErrorChanged -> {
                state = state.copy(passwordError = event.passwordError)
            }
            is EditPasswordEvents.ChangePassword -> {
                state = state.copy(password = event.password)
                passwordState.value.validate(event.password)
                onEvent(EditPasswordEvents.PasswordErrorChanged(passwordState.value.error != null))
            }
            is EditPasswordEvents.ChangeConfirmPassword -> {
                state = state.copy(confirmPassword = event.confirmPassword)
            }
            is EditPasswordEvents.Submit -> {
                submitEditProfile(context)
            }
            is EditPasswordEvents.IsLoadingChanged -> {
                state = state.copy(isLoading = event.isLoading)
            }
            is EditPasswordEvents.WifiCase.Confirm -> {
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is EditPasswordEvents.WifiCase.Deny -> {
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is EditPasswordEvents.ShowDialog -> {
                state = state.copy(
                    showDialog = !state.showDialog
                )
            }
            is EditPasswordEvents.SuccessEditingPasswordChanged -> {
                state = state.copy(successEditing = event.success)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun submitEditProfile(context: Context) {
        InternetConnection.run(context,
            connected = {
                val repeatedPasswordValidate =
                    ValidateConfirmPassword.run(state.password, state.confirmPassword, context)

                if (!repeatedPasswordValidate.success) {
                    state = state.copy(
                        confirmPasswordError = repeatedPasswordValidate.errorMessage
                    )
                }
                val hasError = listOf(
                    state.passwordError, !repeatedPasswordValidate.success
                ).any {
                    it
                }
                if (hasError) {
                    Toast.makeText(
                        context, context.getString(R.string.uncorrect_info),
                        Toast.LENGTH_LONG
                    ).show()
                } else {

                    val resetPassword = ResetPasswordWithTokenRequest(
                        old_password = state.oldPassword,
                        new_password = state.password
                    )
                    viewModelScope.launch {
                        val response = userApiRepo.resetPasswordWithToken(
                            token = TokenManager.getInstance(context).getToken(),
                            resetPassword = resetPassword
                        )
                        response.collect {
                            when (it) {
                                is Resource.Loading -> {
                                    onEvent(EditPasswordEvents.IsLoadingChanged(isLoading = it.isLoading))
                                }
                                is Resource.Success -> {
                                    val apiResponse = it.data
                                    if (apiResponse != null) {
                                        if (apiResponse.Status) {
                                            state = state.copy(
                                                password = "",
                                                oldPassword = "",
                                                confirmPassword = ""
                                            )
                                            onEvent(
                                                EditPasswordEvents.SuccessEditingPasswordChanged(
                                                    true
                                                )
                                            )
                                        } else {
                                            state = state.copy(errorMessage = apiResponse.error)
                                            onEvent(EditPasswordEvents.ShowDialog)
                                        }
                                    }
                                }
                                is Resource.Error -> {
                                    Log.d(TAG, it.message.toString())
                                }
                            }
                        }
                    }
                }
            },
            unconnected = {
                state = state.copy(showInternetAlert = true)
            })
    }
}