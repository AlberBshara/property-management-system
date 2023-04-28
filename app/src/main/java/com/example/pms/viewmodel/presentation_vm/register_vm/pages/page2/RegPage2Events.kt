package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page2


sealed class RegPage2Events {

    data class EmailChanged(val email: String) : RegPage2Events()
    data class PasswordChanged(val password: String) : RegPage2Events()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : RegPage2Events()

}
