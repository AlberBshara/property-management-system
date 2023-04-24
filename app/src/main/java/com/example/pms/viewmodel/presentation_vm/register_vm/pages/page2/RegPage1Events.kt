package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page2


sealed class RegPage1Events {

    data class EmailChanged(val email: String) : RegPage1Events()
    data class PasswordChanged(val password: String) : RegPage1Events()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : RegPage1Events()

}
