package com.example.pms.viewmodel.api.user_services

import com.example.pms.model.LoginUserRequest
import com.example.pms.model.LogoutResponse
import com.example.pms.model.RegisterUserData
import com.example.pms.viewmodel.api.RetrofitClient
import com.example.pms.viewmodel.api.util.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException


class UserServicesRepository(
    private val userServicesInterface: UserServicesInterface = RetrofitClient.userRepository
) {

    suspend fun postRegisterUserData(
        user: RegisterUserData
    ): Flow<Resource<ResponseBody>> {
        return flow {
            emit(Resource.Loading(true))
            val response = try {
                userServicesInterface.postRegisterData(
                    name = user.name.toRequestBody("text/plain".toMediaTypeOrNull()),
                    email = user.email.toRequestBody("text/pain".toMediaTypeOrNull()),
                    password = user.password.toRequestBody("text/plain".toMediaTypeOrNull()),
                    phoneNumber = user.phone_number.toRequestBody("text/pain".toMediaTypeOrNull()),
                    image = user.image
                )
            } catch (e: IOException) {
                emit(Resource.Error(e.message))
                null
            } catch (e: HttpException) {
                emit(Resource.Error(e.response()?.toString()))
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


    suspend fun postLoginUserData(
        user: LoginUserRequest
    ): Flow<Resource<LoginUserRequest.LoginResponse>> {

        return flow {
            emit(Resource.Loading(true))
            val response = try {
                userServicesInterface.postLoginData(user)
            } catch (e: java.lang.Exception) {
                emit(Resource.Error(e.toString()))
                null
            } catch (e: HttpException) {
                emit(Resource.Error(e.toString()))
                null
            }
            response?.let {
                emit(Resource.Success(data = response))
                emit(Resource.Loading(false))
            }
            emit(Resource.Loading(false))
        }
    }


    suspend fun logout(
        token: String
    ): Flow<Resource<LogoutResponse>> = flow {
        emit(Resource.Loading(true))

        val response = try {
            userServicesInterface.logOut(token)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        }
        response?.let {
            emit(Resource.Success(data = response))
            emit(Resource.Loading(false))
        }
        emit(Resource.Loading(false))
    }

}