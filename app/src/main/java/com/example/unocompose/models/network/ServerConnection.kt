package com.example.unocompose.models.network

import android.util.Log
import com.example.unocompose.models.TAG
import com.example.unocompose.models.gson.Message
import io.ktor.network.selector.*
import io.ktor.network.sockets.*

import io.ktor.utils.io.*
import kotlinx.coroutines.*

//TODO remove string and network address kostyil'

class ServerConnection() {
    val counterContext = newSingleThreadContext("CounterContext")
    var isSentCounter = 0
    var clientCount = 0
    val clientList = mutableListOf<String>()
    var gameStarted = false

    var message = Message()
    var broadcastMessage = ""

    suspend fun startServer () {
        Log.d(TAG,"Creating Socket")
        val server = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().bind(port = 25556)
        Log.d(TAG,"Socket created, ip = ${server.localAddress}")

        while (clientCount < 4 && !gameStarted) {
            val clientSocket = server.accept()
            clientList.add(clientSocket.remoteAddress.toString())
            clientCount++


            GlobalScope.launch {
                Log.d("Server","Socket accepted: ${clientSocket.remoteAddress}")

                val input = clientSocket.openReadChannel()
                val output = clientSocket.openWriteChannel(autoFlush = true)
                // TODO notify all users broadcastMessage =

                GlobalScope.launch{
                    try {
                        while(true) {
                            val receivedMessage = input.readUTF8Line()
                            Log.d("Server message listener", "${clientSocket.remoteAddress}: $receivedMessage")
                            //TODO message handler
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                        clientSocket.close()

                    }
                }
                GlobalScope.launch{
                    while(true) {
                        if (message.ip == clientSocket.remoteAddress.toString()){
                            output.writeStringUtf8(message.data)
                            message = Message()
                            // TODO mb need to send Message as parameter
                            Log.d("Server message sender", "${clientSocket.remoteAddress}:  ${message.data}")
                        }
                    }
                }

                //TODO broadcast messages
            }

        }

    }

    fun sendMessage(dataToSend: String, ip: String) {
        message = Message(data = dataToSend, ip = ip)
    }

}



data class LobbyEntry (val name: String, val userCount: String)