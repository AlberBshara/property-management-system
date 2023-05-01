package com.example.pms.viewmodel.preferences

import android.content.Context

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
        sharedPreferences.setData(PMSSharedPreferenceHelper.TOKEN, token)
    }

    fun getToken(): String =
        sharedPreferences.getData(PMSSharedPreferenceHelper.TOKEN)


    fun clearAllData() {
        sharedPreferences.editor.clear().apply()
    }

    fun clearDataOfKey(key: String) {
        sharedPreferences.editor.remove(key).apply()
    }


}