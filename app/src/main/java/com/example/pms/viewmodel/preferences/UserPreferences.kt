package com.example.pms.viewmodel.preferences

import android.content.Context

object UserPreferences {

    fun saveUserData(userData: UserDataPreference, context: Context) {
        val sharedPreferencesUsage = SharedPreferencesUsage(context)
        sharedPreferencesUsage.setUserId(userData.id)
        sharedPreferencesUsage.setEmail(userData.email)
    }

    fun getUserData(context: Context): UserDataPreference {
        val sharedPreferencesUsage = SharedPreferencesUsage(context)
        return UserDataPreference(
            sharedPreferencesUsage.getUserID(),
            sharedPreferencesUsage.getUsername()
        )
    }

    fun setId(userId: Int, context: Context) {
        val sharedPreferencesUsage = SharedPreferencesUsage(context)
        sharedPreferencesUsage.setUserId(userId)
    }

    fun clear(context: Context) {
        val sharedPreferencesUsage = SharedPreferencesUsage(context)
        sharedPreferencesUsage.clearDataOfKey(PMSSharedPreferenceHelper.EMAIL)
        sharedPreferencesUsage.clearDataOfKey(PMSSharedPreferenceHelper.USER_ID)
    }

    data class UserDataPreference(
        val id: Int,
        val email : String
    )
}