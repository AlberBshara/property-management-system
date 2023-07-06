package com.example.pms.viewmodel.api.chatting_services

import com.example.pms.model.AllChattedResponse
import com.example.pms.viewmodel.api.RetrofitClient
import com.example.pms.viewmodel.api.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ChattingServicesImpl(
    private val chattingServicesInterface: ChattingServicesInterface = RetrofitClient.chattingRepository
) {


    suspend fun fetchAllChattedPersons(
        token: String
    ): Flow<Resource<AllChattedResponse>> = flow {
        emit(Resource.Loading(false))
        val chattedResponse = try {
            chattingServicesInterface.fetchAllChatted(
                token
            )
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
            null
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.response().toString()))
            null
        }
        chattedResponse?.let {
            emit(Resource.Success(data = it))
            emit(Resource.Loading(false))
        }
        emit(Resource.Loading(false))
    }
}