package com.example.pms.viewmodel.preferences

import android.content.Context
import android.content.SharedPreferences

class PMSSharedPreferenceHelper(context: Context) {

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SP_NAME,
        SP_MODE
    )
    val editor: SharedPreferences.Editor = sharedPreferences.edit()


    inline fun <reified T : Any> setData(key: String, data: T) {
        when (T::class) {
            String::class -> editor.putString(key, data.toString())
            Int::class -> editor.putInt(key, data as Int)
            Boolean::class -> editor.putBoolean(key, data as Boolean)
            Float::class -> editor.putFloat(key, data as Float)
            Long::class -> editor.putLong(key, data as Long)
            else -> throw IllegalArgumentException("Invalid data type: ${T::class.simpleName}")
        }
        editor.apply()
    }

    inline fun <reified  T: Any> getData(key: String) : T =
        when(T::class) {
            String::class -> sharedPreferences.getString(key, DEFAULT_VALUE) as T
            Int::class -> sharedPreferences.getInt(key,-1) as T
            Float::class -> sharedPreferences.getFloat(key, 0.0f) as T
            Boolean::class -> sharedPreferences.getBoolean(key, false) as T
            Long::class -> sharedPreferences.getLong(key, 0L) as T
            else -> throw IllegalArgumentException("invalid data according to the key : [$key]")
        }


    companion object {
        private const val SP_MODE = Context.MODE_PRIVATE
        private const val SP_NAME = "property management system"
        const val DEFAULT_VALUE  = "default value"
        const val USER_NAME = "username"
        const val USER_ID = "user_id"
        const val EMAIL = "email"
        const val PASSWORD = "password"
        const val TOKEN = "token"
        const val LANGUAGE = "language"
        const val VERIFY = "verification"
        const val COMPLETE: String = "complete"
    }
}
