package com.example.pms.viewmodel.api.util

sealed class Resource<T>(
    val data : T? = null, message : String? = null ) {
    class Success<T>(data : T) : Resource<T>(data)
    class Error<T>(message: String? , data : T? = null) : Resource<T>(data, message)
    class Loading<T>(val isLoading : Boolean) : Resource<T>()
}

