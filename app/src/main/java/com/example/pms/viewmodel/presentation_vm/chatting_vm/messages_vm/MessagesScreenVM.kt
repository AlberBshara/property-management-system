package com.example.pms.viewmodel.presentation_vm.chatting_vm.messages_vm

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pms.model.ConversationMessageModel
import com.example.pms.model.PreviousMessagesModel
import com.example.pms.model.SendMessageModel
import com.example.pms.model.dto.ChatMessage
import com.example.pms.model.dto.Sender
import com.example.pms.viewmodel.api.chatting_services.ChattingServicesImpl
import com.example.pms.viewmodel.api.chatting_services.pusher.PusherPMSKeys
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.utils.TokenManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.PrivateChannelEventListener
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import com.pusher.client.util.HttpAuthorizer
import kotlinx.coroutines.launch
import java.lang.Exception

class MessagesScreenVM(
    private val chattingServicesImpl: ChattingServicesImpl = ChattingServicesImpl()
) : ViewModel() {

    var state by mutableStateOf(MessagesState())

    companion object {
        private const val TAG: String = "MessagesScreenVM.kt"
    }


    private var counter: Int = 0

    fun onEvent(event: MessagesEvents) {
        when (event) {
            is MessagesEvents.OnMessageChanging -> {
                state = state.copy(
                    currentMessage = event.message
                )
            }
            is MessagesEvents.SendMessage -> {
                sendMessage(event.context, event.receiverId, event.message)
            }
            is MessagesEvents.OnStart -> {
                showPreviousConversation(event.context, event.receiverId, event.receiverUserName)
            }
        }
    }


    private fun showPreviousConversation(
        context: Context,
        receiverId: Int,
        receiverUserName: String
    ) {
        if (counter == 0) {
            this.counter++
            openChannel(receiverId)
            state = state.copy(
                username = receiverUserName
            )
            viewModelScope.launch {
                val conversationResponse = chattingServicesImpl.fetchAllPreviousConversations(
                    PreviousMessagesModel(receiverId),
                    TokenManager.getInstance(context).getToken()
                )
                conversationResponse.collect {
                    when (it) {
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = it.isLoading
                            )
                            Log.d(TAG, "showPreviousConversation: isLoading : ${it.isLoading}")
                        }
                        is Resource.Success -> {
                            it.data?.let { responseBody ->
                                val gson = Gson()
                                val apiResponse = gson.fromJson<List<ConversationMessageModel>>(
                                    responseBody.charStream(),
                                    object : TypeToken<List<ConversationMessageModel>>() {}.type
                                )
                                apiResponse?.let { messagesList ->
                                    if (messagesList.isNotEmpty()) {

                                        val finalMessagesList = messagesList.map { mappedList ->
                                            if (receiverId == mappedList.senderId) {
                                                ChatMessage(
                                                    mappedList.message, Sender.UserB,
                                                    tryAgain = false,
                                                    isSending = false,
                                                    sentSuccessfully = true
                                                )
                                            } else {
                                                ChatMessage(
                                                    mappedList.message, Sender.UserA,
                                                    tryAgain = false,
                                                    isSending = false,
                                                    sentSuccessfully = true
                                                )
                                            }
                                        }
                                        state = state.copy(
                                            chatMessages = finalMessagesList
                                        )
                                        Log.d(
                                            TAG,
                                            "showPreviousConversation: Success $finalMessagesList"
                                        )
                                    }
                                }
                            }
                        }
                        is Resource.Error -> {

                        }
                    }
                }
            }
        }
    }


    private fun sendMessage(
        context: Context,
        receiverId: Int,
        message: String
    ) {
        if (message.isNotBlank()) {
            addMessage(message)
            viewModelScope.launch {
                val response = chattingServicesImpl.sendMessage(
                    TokenManager.getInstance(context).getToken(),
                    SendMessageModel(receiverId, message)
                )
                response.collect {
                    when (it) {
                        is Resource.Loading -> {
                            Log.d(TAG, "sendMessage: Loading ${it.isLoading}")
                        }
                        is Resource.Success -> {
                            it.data?.let { res ->
                                for (i in state.chatMessages.size..0) {
                                    if (state.chatMessages[i].text == message){
                                        state.chatMessages.toMutableList()[i].copy(
                                            text= message ,
                                            sender = Sender.UserA,
                                            tryAgain = false,
                                            isSending = false,
                                            sentSuccessfully = true
                                        )

                                    }
                                }
                                Log.d(TAG, "sendMessage: Success ${res.string()}")
                            }
                        }
                        is Resource.Error -> {

                        }
                    }
                }
            }
        }
    }

    private fun addMessage(
        message: String
    ) {
        val updatedMessageList = state.chatMessages.toMutableList()
        updatedMessageList.add(
            ChatMessage(
                message, Sender.UserA,
                tryAgain = false,
                isSending = true,
                sentSuccessfully = false
            )
        )
        state = state.copy(
            chatMessages = updatedMessageList,
            currentMessage = ""
        )
    }




    private fun openChannel(
        receiverId : Int
    ){
        viewModelScope.launch {
            val options = PusherOptions()
            options.setCluster(PusherPMSKeys.CLUSTER)
            options.authorizer = HttpAuthorizer("https://a938-89-38-99-16.ngrok-free.app/pusher/auth")

            val pusher = Pusher(PusherPMSKeys.KEY, options)
            pusher.connect(
                object : ConnectionEventListener {
                    override fun onConnectionStateChange(p0: ConnectionStateChange?) {
                        Log.i(TAG, "State changed from ${p0?.previousState} to ${p0?.currentState}")
                    }
                    override fun onError(p0: String?, p1: String?, p2: Exception?) {
                        Log.i("Pusher", "There was a problem connecting! code ($p0), message ($p2)")
                    }
                }, ConnectionState.ALL
            )

            pusher.connect()

            val channel = pusher.subscribePrivate("private-chat$receiverId")
            channel.bind("chatMessage", object : PrivateChannelEventListener {
                override fun onSubscriptionSucceeded(channelName: String?) {
                    Log.i(TAG, "Subscribed to private channel: $channelName")
                }

                override fun onEvent(channelName: String?, eventName: String?, data: String?) {
                    Log.i(TAG, "Received event on channel $channelName, event: $eventName, data: $data")
                }

                override fun onAuthenticationFailure(message: String?, e: Exception?) {
                    Log.i(TAG, "Private channel authentication failed: $message")
                }
            })
        }
    }
}