package com.example.chat

import java.io.Serializable

data class Message(
    var senderUid: String = "",
    var sended_date: String = "",
    var message: String = "",
    var confirmed:Boolean=false
): Serializable {
}