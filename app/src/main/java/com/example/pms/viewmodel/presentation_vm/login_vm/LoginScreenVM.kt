package com.example.pms.viewmodel.presentation_vm.login_vm


import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.pms.R
import com.example.pms.viewmodel.destinations.Destination
import com.example.pms.viewmodel.presentation_vm.login_vm.validation.EmailState
import com.example.pms.viewmodel.presentation_vm.login_vm.validation.PasswordState


class LoginScreenVM : ViewModel() {


    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    fun setContext(context: Context) {
        this.context = context
    }

    var state by mutableStateOf(LoginState())

    val emailState by lazy { mutableStateOf(EmailState(context)) }
    val passwordState by lazy { mutableStateOf(PasswordState(context)) }


    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.EmailChanged -> {
                state = state.copy(email = event.email)
                emailState.value.validate(event.email)
                onEvent(LoginEvents.EmailErrorChanged(emailState.value.error != null))
            }
            is LoginEvents.EmailErrorChanged -> {
                state = state.copy(emailError = event.emailError)
            }
            is LoginEvents.PasswordChanged -> {
                state = state.copy(password = event.password)
                passwordState.value.validate(event.password)
                onEvent(LoginEvents.PasswordErrorChanged(passwordState.value.error != null))
            }
            is LoginEvents.PasswordErrorChanged -> {
                state = state.copy(passwordError = event.passwordError)
            }
            is LoginEvents.ForgotMyPassword -> {
                event.navController.navigate(Destination.ForgetPasswordDestination.route)
            }
            is LoginEvents.CreateNewAccount -> {
                event.navController.navigate(Destination.RegisterDestination.route)
            }
            is LoginEvents.Submit -> {
                login(event.navController)
            }

        }
    }


    private fun login(
        navController: NavHostController
    ) {
        val hasError = listOf(
            state.emailError, state.passwordError
        ).any {
            it
        }
        if (hasError) {
            Toast.makeText(context, context.getString(R.string.uncorrect_info),
                Toast.LENGTH_LONG)
                .show()
        } else {
            navController.popBackStack()
            navController.navigate(Destination.DashboardDestination.route)
        }

    }


}