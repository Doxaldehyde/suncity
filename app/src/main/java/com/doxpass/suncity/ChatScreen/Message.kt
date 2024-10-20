package com.dox.suncity.ChatScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


data class Message(val text: String = "",
                  val isSent: Boolean = false,
                   val senderName: String = "")

class ChatViewModel : ViewModel() {
    val messages = mutableStateListOf<Message>()

}
