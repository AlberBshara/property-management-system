package com.example.pms.viewmodel.api.user_services

import com.example.pms.model.RegisterUserData
import com.example.pms.viewmodel.api.RetrofitClient
import com.example.pms.viewmodel.api.util.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import okhttp3.Response


class UserServicesImplementation(
    private val userApi: UserServicesInterface = RetrofitClient.userRepository
) {

     suspend fun postRegisterUserData(
        user: RegisterUserData
    ): Flow<Resource<Response>> {
        return flow {
            emit(Resource.Loading(true))
            val response = try {
                userApi.postRegisterData(
                    user.name, user.email, user.password, user.phone_number
                )
            } catch (e: Exception) {
                emit(Resource.Error("couldn't upload the user data"))
                null
            }
            response?.let {
                emit(Resource.Success(
                    data = response
                ))
                emit(Resource.Loading(false))
            }
            emit(Resource.Loading(false))
        }
    }

 }