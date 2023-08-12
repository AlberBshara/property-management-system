package com.example.pms.viewmodel.presentation_vm.profile_vm.edit_profile_vm

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
import com.example.pms.model.EditUserProfileRequest
import com.example.pms.viewmodel.api.user_services.UserServicesRepository
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.presentation_vm.register_vm.validation.user_cases.ValidationName
import com.example.pms.viewmodel.utils.ImageHelper
import com.example.pms.viewmodel.utils.InternetConnection
import com.example.pms.viewmodel.utils.TokenManager
import kotlinx.coroutines.launch
import com.example.pms.R
import com.example.pms.viewmodel.destinations.Destination


class EditProfileScreenVM(
    private val userApiRepository: UserServicesRepository = UserServicesRepository()
) : ViewModel() {

    companion object {
        private const val TAG: String = "EditProfileScreenVM.kt"
    }

    private var count = 0
    var state by mutableStateOf(EditProfileState())

    @RequiresApi(Build.VERSION_CODES.M)
    fun onEvent(event: EditProfileEvents) {
        when (event) {
            is EditProfileEvents.GetUserData -> {
                getUserData(event.context)
            }
            is EditProfileEvents.ChangeFirstName -> {
                state = state.copy(firstName = event.firstName)
            }
            is EditProfileEvents.ChangeLastName -> {
                state = state.copy(lastName = event.lastName)
            }
            is EditProfileEvents.ChangePhoneNumber -> {
                state = state.copy(phone = event.phone)
            }
            is EditProfileEvents.ChangeImage -> {
                state = state.copy(imageSend = event.image)
                submitImage(event.context)
            }
            is EditProfileEvents.ChangeImageReceive -> {
                state = state.copy(imageReceive = event.imageReceive)
            }
            is EditProfileEvents.Submit -> {
                submitEditProfile(event.context)
            }
            is EditProfileEvents.ResetPasswordButton -> {
                event.navHostController.navigate(Destination.EditPasswordScreen.route)
            }
            is EditProfileEvents.EditImageDialogOnChange -> {
                state = state.copy(editImageDialog = !state.editImageDialog)
            }
            is EditProfileEvents.IsLoadingChanged -> {
                state = state.copy(isLoading = event.isLoading)
            }
            is EditProfileEvents.WifiCase.Confirm -> {
                state = state.copy(
                    showInternetAlert = false
                )
            }
            is EditProfileEvents.WifiCase.Deny -> {
                state = state.copy(
                    showInternetAlert = false
                )
            }
            else -> {}
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun submitEditProfile(
        context: Context
    ) {
        InternetConnection.run(context,
            connected = {
                val firstnameValidate = ValidationName.run(state.firstName, context)
                val lastnameValidate = ValidationName.run(state.lastName, context)
                val hasError = listOf(
                    firstnameValidate, lastnameValidate
                ).any { !it.success }

                if (hasError) {
                    state = state.copy(
                        firstNameError = firstnameValidate.errorMessage,
                        lastNameError = lastnameValidate.errorMessage,
                    )
                } else {
                    state = state.copy(
                        firstNameError = null,
                        lastNameError = null,
                    )
                    val infoUser = EditUserProfileRequest(
                        name = state.firstName + " " + state.lastName,
                        phoneNumber = state.phone
                    )
                    viewModelScope.launch {
                        val response = userApiRepository.postUpdateUserInfo(
                            token = TokenManager.getInstance(context).getToken(),
                            userInfo = infoUser
                        )
                        response.collect {
                            when (it) {
                                is Resource.Loading -> {
                                    onEvent(EditProfileEvents.IsLoadingChanged(isLoading = it.isLoading))
                                }
                                is Resource.Success -> {
                                    val apiResponse = it.data
                                    if (apiResponse != null) {
                                        if (apiResponse.status) {
                                            Toast.makeText(
                                                context,
                                                context.getString(R.string.added_info),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                context, context.getString(R.string.try_later),
                                                Toast.LENGTH_LONG
                                            ).show()
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


    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun getUserData(
        context: Context
    ) {
        if (count == 0) {
            count++
            viewModelScope.launch {
                val response =
                    userApiRepository.getUserProfile(TokenManager.getInstance(context).getToken())
                response.collect {
                    when (it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isStarting = it.isLoading
                            )
                        }
                        is Resource.Success -> {
                            if (it.data != null) {
                                val fullName = it.data.user.name
                                val names = fullName.split(" ")
                                val firstName = names[0]
                                val secondName = names[1]
                                    onEvent(EditProfileEvents.ChangeImageReceive(imageReceive = it.data.user.image ?: ""))
                                onEvent(EditProfileEvents.ChangePhoneNumber(phone = it.data.user.phoneNumber))
                                onEvent(EditProfileEvents.ChangeFirstName(firstName = firstName))
                                onEvent(EditProfileEvents.ChangeLastName(lastName = secondName))
                            }
                        }
                        is Resource.Error -> {
                            Log.d(TAG, it.message.toString())
                        }
                    }
                }
            }
        }
    }


    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun submitImage(context: Context) {
        val imageFile = ImageHelper.uriToMultipart(context, state.imageSend!!, "image")

        viewModelScope.launch {
            val response =
                userApiRepository.postEditUserImage(
                    token = TokenManager.getInstance(context).getToken(),
                    image = imageFile
                )
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        onEvent(EditProfileEvents.IsLoadingChanged(isLoading = it.isLoading))
                    }
                    is Resource.Success -> {
                        val apiResponse = it.data
                        if (apiResponse != null) {
                            if (apiResponse.status)
                                Toast.makeText(
                                    context, context.getString(R.string.added_successfully),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            count = 0
                            getUserData(context)
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.try_later), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Error -> {
                        Log.d(TAG, it.message.toString())
                    }
                }
            }
        }
    }

}