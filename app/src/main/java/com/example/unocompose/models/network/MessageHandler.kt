package com.example.unocompose.models.network

import com.example.unocompose.models.gson.Message


abstract class MessageHandler {
    abstract fun send(type: String)

    abstract fun receive()

    fun serialize(){

    }
}