package com.example.pms.viewmodel.presentation_vm.chatting_vm.messages_vm

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pms.R
import com.example.pms.model.ConversationMessageModel
import com.example.pms.model.PreviousMessagesModel
import com.example.pms.model.SendMessageModel
import com.example.pms.model.dto.ChatMessage
import com.example.pms.model.dto.Sender
import com.example.pms.viewmodel.api.chatting_services.ChattingServicesImpl
import com.example.pms.viewmodel.api.chatting_services.pusher.PusherController
import com.example.pms.viewmodel.api.util.Resource
import com.example.pms.viewmodel.utils.TokenManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

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
            openChannel(context)
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
                                    if (state.chatMessages[i].text == message) {
                                        state.chatMessages.toMutableList()[i].copy(
                                            text = message,
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
        context: Context
    ) {
        val popMessage : MediaPlayer = MediaPlayer.create(context , R.raw.pop_message)
        viewModelScope.launch {
            PusherController.openChannel(context,
                onReceiveMessageListener = {
                    popMessage.start()
                    Log.d(TAG, "openChannel: ${it.message}")
                    val updatedList = ArrayList(state.chatMessages)
                    updatedList.add(ChatMessage(
                        text = it.message, sender = Sender.UserB,
                        tryAgain = false,
                        isSending = true,
                        sentSuccessfully = true
                    ))
                    state = state.copy(
                        chatMessages = updatedList
                    )
                }, onErrorListener = { messageError, code, exception ->
                })
        }
    }
}