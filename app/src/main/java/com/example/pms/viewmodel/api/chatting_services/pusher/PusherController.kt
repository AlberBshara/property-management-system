package com.example.pms.viewmodel.api.chatting_services.pusher

import android.content.Context
import android.util.Log
import com.example.pms.model.ChatRealTimeModel
import com.example.pms.viewmodel.preferences.UserPreferences
import com.google.gson.Gson
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import retrofit2.HttpException
import java.io.IOException
import kotlin.Exception

object PusherController {

    private const val SUBSCRIBE_NAME: String = "chat"
    private const val CHANNEL_NAME: String = "chatMessage"

    fun openChannel(
        context: Context,
        onReceiveMessageListener: (ChatRealTimeModel.Chat) -> Unit,
        onErrorListener: (message: String, code: String, exception: Exception) -> Unit
    ) {
        try {
            val userId: Int = UserPreferences.getUserData(context).id

            val options = PusherOptions()
            options.setCluster(PusherPMSKeys.CLUSTER)

            val pusher = Pusher(PusherPMSKeys.KEY, options)

            pusher.connect(object : ConnectionEventListener {

                override fun onConnectionStateChange(change: ConnectionStateChange) {
                    Log.i("Pusher", "State changed from ${change.previousState} to ${change.currentState}")
                }
                override fun onError(
                    message: String,
                    code: String,
                    e: Exception
                ) {
                    onErrorListener(message, code, e)
                }
            }, ConnectionState.ALL)

            val channel = pusher.subscribe("$SUBSCRIBE_NAME$userId")
            channel.bind(CHANNEL_NAME) { subscribeName: String, channelName: String, data ->
                val gson = Gson()
                val apiResponse = gson.fromJson(
                    data,
                    ChatRealTimeModel::class.java
                )
                onReceiveMessageListener(apiResponse.receivedObject)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: HttpException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}