package com.example.justicia.model

class Message(@JvmField var message: String, @JvmField var sentBy: String) {

    companion object {
        @JvmField
        var SENT_BY_ME = "me"
        var SENT_BY_BOT = "bot"
    }
}