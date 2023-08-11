package com.example.pms.model.dto


data class ChatMessage(
    val text: String,
    val sender: Sender,
    val tryAgain: Boolean,
    val isSending: Boolean,
    val sentSuccessfully: Boolean,
    val time : String
)

/**
 * assuming that UserA is the Sender and UserB is the receiver.
 */
enum class Sender {
    UserA, UserB
}
