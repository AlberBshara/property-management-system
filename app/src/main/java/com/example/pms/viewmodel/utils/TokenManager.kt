package com.example.pms.viewmodel.utils

import android.annotation.SuppressLint
import android.content.Context
import com.example.pms.viewmodel.preferences.PMSSharedPreferenceHelper
import com.example.pms.viewmodel.preferences.SharedPreferencesUsage


class TokenManager private constructor() {


    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var INSTANCE: TokenManager
        private lateinit var sharedPreferencesUsage: SharedPreferencesUsage

        fun getInstance(context: Context): TokenManager {
            if (this.INSTANCE == null) {
                this.INSTANCE = TokenManager()
            }
            this.sharedPreferencesUsage = SharedPreferencesUsage(context)
            return this.INSTANCE
        }
    }

    fun save(token: String) {
        sharedPreferencesUsage.setToken(token)
    }

    fun clear() {
        sharedPreferencesUsage.clearDataOfKey(
            PMSSharedPreferenceHelper.TOKEN
        )
    }

    fun exits(): Boolean =
        sharedPreferencesUsage.getToken() != PMSSharedPreferenceHelper.DEFAULT_VALUE

    fun getToken() : String =
        "Bearer ${sharedPreferencesUsage.getToken()}"

}



