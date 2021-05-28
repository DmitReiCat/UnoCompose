package com.example.unocompose.models.network

import com.example.unocompose.models.gson.Message


abstract class MessageHandler {
    abstract fun send(message: Message)
    abstract fun getCard()
    abstract fun initialise()


}