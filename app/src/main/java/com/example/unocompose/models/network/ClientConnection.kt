package com.example.unocompose.models.network

import android.util.Log
import com.example.unocompose.models.GameData
import com.example.unocompose.models.gson.Message
import com.google.gson.Gson
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import java.net.InetAddress
import java.net.InetSocketAddress
import java.util.concurrent.Executors

object ClientConnection {
    //    val availableLobbies = mutableSetOf(listOf())
    val coroutineContext =
        Executors.newSingleThreadExecutor().asCoroutineDispatcher() + CoroutineName("Client data")
    var data = ""


    suspend fun connect(ipAddress: InetAddress) {
        val serverSocket = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp()
            .connect(InetSocketAddress(ipAddress, 25556))

        val input = serverSocket.openReadChannel()
        val output = serverSocket.openWriteChannel(autoFlush = true)


        GlobalScope.launch {
            try {
                while (true) {
                    val receivedMessage = input.readUTF8Line()
                    Log.d(
                        "Client message listener",
                        "${serverSocket.remoteAddress}: $receivedMessage"
                    )

                    //TODO message handler
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                serverSocket.close()
            }
        }
        GlobalScope.launch {
            while (true) {
                if (data != "") {
                    output.writeStringUtf8(data)
                    output.writeStringUtf8("\n")
                    // TODO mb need to send Message as parameter
                    Log.d("Client message", "${serverSocket.remoteAddress}:  ${data}")
                    data = ""
                }

            }
        }
    }

    fun sendData(dataToSend: Message) {
        while (data != "") {
            continue
        }
        var gson = Gson()
        val newData = Message(ip = "", data = "Hello", type = "NameInit")
        var jsonString = gson.toJson(dataToSend)
        data = jsonString
    }


}

