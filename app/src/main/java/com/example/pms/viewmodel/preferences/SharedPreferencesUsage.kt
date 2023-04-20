package com.example.pms.viewmodel.preferences

import android.content.Context

class SharedPreferencesUsage(
    context : Context
) {

    private val sharedPreferences = PMSSharedPreferenceHelper(context)

    fun setUsername(username : String) {
        sharedPreferences.setData( PMSSharedPreferenceHelper.USER_NAME, username)
    }

    fun getUsername() : String
      = sharedPreferences.getData(PMSSharedPreferenceHelper.USER_NAME)



}