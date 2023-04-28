package com.example.pms.viewmodel.presentation_vm.register_vm.pages.page1

sealed class RegPage1Events {
   data class FirstNameChanged(val firstname : String ) : RegPage1Events()
   data class LastNameChanged(val lastname : String) : RegPage1Events()
   data class PhoneNumberChanged(val phoneNumber : String,
                                 val countryCode : String ) : RegPage1Events()
}
