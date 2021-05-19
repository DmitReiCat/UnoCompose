package com.example.unocompose.models

import android.util.Log
import io.ktor.network.selector.*
import io.ktor.network.sockets.*

import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ServerConnection() {

    suspend fun createChannels () {
        Log.d(TAG,"Creating Socket")
        val server = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().bind(port = 25556)
        Log.d(TAG,"Socket createdm, ip = ${server.localAddress}")

        while (true) {
            val clientSocket = server.accept()
            GlobalScope.launch {
                Log.d(TAG,"Socket accepted: ${clientSocket.remoteAddress}")

                val input = clientSocket.openReadChannel()
                val output = clientSocket.openWriteChannel(autoFlush = true)

                try {
                    while (true) {
                        val line = input.readUTF8Line()
                        Log.d(TAG, "${clientSocket.remoteAddress}: $line")
                        output.writeStringUtf8("hello!")
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    clientSocket.close()
                }

            }

        }

    }


//    suspend fun createChannels() {
//        val server = ServerSocket(25556)
//
//        while (true) {
//            GlobalScope.launch {
//                Log.d(TAG, "Strat listening on $server")
//                val clientSocket = server.accept()
//                try {
//                    while (true) {
//                        clientSocket.getInputStream().bufferedReader().use {
//                            val message = it.readLine()
//                            Log.d(TAGNSD, message)
//                        }
//                    }
//                } catch (e: Throwable) {
//                    e.printStackTrace()
//                    server.close()
//                }
//            }
//        }
//    }

}



data class LobbyEntry (val name: String, val userCount: String)