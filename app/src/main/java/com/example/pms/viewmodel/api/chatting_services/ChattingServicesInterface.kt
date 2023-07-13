package com.example.pms.viewmodel.api.chatting_services

import com.example.pms.model.AllChattedResponse
import com.example.pms.model.PreviousMessagesModel
import com.example.pms.model.SendMessageModel
import com.example.pms.viewmodel.api.util.Keys
import com.example.pms.viewmodel.api.util.Urls
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ChattingServicesInterface {


    @GET(Urls.CHATTED_PERSONS_END_POINT)
    suspend fun fetchAllChatted(
        @Header(Keys.AUTHORIZATION) authToken: String
    ): AllChattedResponse


    @POST(Urls.CONVERSATION_END_POINT)
    suspend fun fetchAllPreviousConversations(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Body previousConversationsRequest: PreviousMessagesModel
    ): ResponseBody


    @POST(Urls.SEND_MESSAGE_END_POINT)
    suspend fun sendMessage(
        @Header(Keys.AUTHORIZATION) authToken: String,
        @Body sentMessage: SendMessageModel
    ): ResponseBody

}