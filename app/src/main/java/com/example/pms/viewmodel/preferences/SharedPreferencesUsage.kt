package com.example.pms.viewmodel.preferences

import android.content.Context
import com.example.pms.viewmodel.api.util.Keys

class SharedPreferencesUsage(
    context: Context
) {

    private val sharedPreferences = PMSSharedPreferenceHelper(context)

    fun setUsername(username: String) {
        sharedPreferences.setData(PMSSharedPreferenceHelper.USER_NAME, username)
    }
    fun getUsername(): String =
        sharedPreferences.getData(PMSSharedPreferenceHelper.USER_NAME)


    fun setToken(token: String) {
        sharedPreferences.setData(Keys.TOKEN, token)
    }
    fun getToken(): String =
        sharedPreferences.getData(Keys.TOKEN)


    fun clearAllData() {
        sharedPreferences.editor.clear().apply()
    }


}