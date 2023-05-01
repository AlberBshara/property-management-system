package com.example.pms.viewmodel.api.user_services

import com.example.pms.model.RegisterUserData
import com.example.pms.viewmodel.api.RetrofitClient
import com.example.pms.viewmodel.api.util.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody


class UserServicesImplementation(
    private val userRepository: UserServicesInterface = RetrofitClient.userRepository
) {

    suspend fun postRegisterUserData(
        user: RegisterUserData
    ): Flow<Resource<ResponseBody>> {
        return flow {
            emit(Resource.Loading(true))
            val response = try {
                userRepository.postRegisterData(user)
            } catch (e: Exception) {
                emit(Resource.Error(e.message))
                null
            }
            response?.let {
                emit(
                    Resource.Success(
                        data = response
                    )
                )
                emit(Resource.Loading(false))
            }
            emit(Resource.Loading(false))
        }
    }
}