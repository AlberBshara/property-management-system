package com.example.pms.viewmodel.api.chatting_services

import com.example.pms.model.AllChattedResponse
import com.example.pms.model.PreviousMessagesModel
import com.example.pms.model.SendMessageModel
import com.example.pms.viewmodel.api.RetrofitClient
import com.example.pms.viewmodel.api.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException

class ChattingServicesImpl(
    private val chattingServicesInterface: ChattingServicesInterface = RetrofitClient.chattingRepository
) {

    suspend fun fetchAllChattedPersons(
        token: String
    ): Flow<Resource<AllChattedResponse>> = flow {
        emit(Resource.Loading(true))
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


    suspend fun fetchAllPreviousConversations(
        previousConversationsRequest: PreviousMessagesModel,
        token: String
    ): Flow<Resource<ResponseBody>> = flow {
        emit(Resource.Loading(true))
        val conversationResponse = try {
            chattingServicesInterface.fetchAllPreviousConversations(
                token, previousConversationsRequest
            )
        } catch (e: IOException) {
            emit(Resource.Error(message = e.toString()))
            null
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.response().toString()))
            null
        }
        conversationResponse?.let {
            emit(Resource.Success(data = it))
            emit(Resource.Loading(false))
        }
        emit(Resource.Loading(false))
    }


    suspend fun sendMessage(
        token: String, sendMessageModel: SendMessageModel
    ): Flow<Resource<ResponseBody>> = flow {
        emit(Resource.Loading(true))
        val response = try {
            chattingServicesInterface.sendMessage(
                token, sendMessageModel
            )
        } catch (e: IOException) {
            emit(Resource.Error(message = e.toString()))
            null
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.response().toString()))
            null
        }
        response?.let {
            emit(Resource.Success(data = it))
            emit(Resource.Loading(false))
        }
        emit(Resource.Loading(false))
    }
}