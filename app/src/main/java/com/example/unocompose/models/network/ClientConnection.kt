package com.example.unocompose.models.network

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import java.io.ByteArrayInputStream
import java.net.InetSocketAddress

object ClientConnection {
//    val availableLobbies = mutableSetOf(listOf())

    lateinit var input:ByteReadChannel
    lateinit var output:ByteWriteChannel

    suspend fun connect(ipAdress: String){
        val socket = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().connect(InetSocketAddress(ipAdress, 25556))
        input = socket.openReadChannel()
        output = socket.openWriteChannel(autoFlush = true)
        }
    suspend fun sendMessage(message: String){
        output.writeStringUtf8("hello server!")
    }
    suspend fun acceptMessage(): String? {
        return(input.readUTF8Line())
    }


}