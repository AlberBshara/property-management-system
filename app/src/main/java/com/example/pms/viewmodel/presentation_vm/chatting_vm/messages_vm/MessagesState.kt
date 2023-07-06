package com.example.pms.viewmodel.presentation_vm.chatting_vm.messages_vm

data class MessagesState(
    val chatMessages : List<ChatMessage> = listOf(
        ChatMessage("hey" , Sender.UserA),
        ChatMessage("hey" , Sender.UserB),
        ChatMessage("how you foin" , Sender.UserA),
        ChatMessage("asd asd asd asd" , Sender.UserA),
        ChatMessage("asd asd" , Sender.UserB),
        ChatMessage("hey" , Sender.UserA),
        ChatMessage("hey" , Sender.UserB),
        ChatMessage("how you foin" , Sender.UserA),
        ChatMessage("asd asd asd asd" , Sender.UserA),
        ChatMessage("asd asd" , Sender.UserB),
        ChatMessage("hey" , Sender.UserA),
        ChatMessage("hey" , Sender.UserB),
        ChatMessage("how you foin" , Sender.UserA),
        ChatMessage("asd asd asd asd" , Sender.UserA),
        ChatMessage("asd asd" , Sender.UserB),

        )
) {
    data class ChatMessage(val text: String, val sender: Sender)

        enum class Sender {
            UserA, UserB
        }
}



