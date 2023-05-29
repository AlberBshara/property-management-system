package com.example.pms.viewmodel.preferences

import android.content.Context

object UserPreferences {

    fun saveUserData(userData: UserDataPreference, context: Context) {
        val sharedPreferencesUsage = SharedPreferencesUsage(context)
        sharedPreferencesUsage.setUserId(userData.id)
        sharedPreferencesUsage.setUsername(userData.username)
        sharedPreferencesUsage.setEmail(userData.email)
    }

    fun getUserData(context: Context): UserDataPreference {
        val sharedPreferencesUsage = SharedPreferencesUsage(context)
        return UserDataPreference(
            sharedPreferencesUsage.getUserID(),
            sharedPreferencesUsage.getUsername(),
            sharedPreferencesUsage.getEmail()
        )
    }

    data class UserDataPreference(
        val id: Int,
        val username: String,
        val email : String ,
    )
}