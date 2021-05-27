package com.example.unocompose.models.network

import android.util.Log
import com.example.unocompose.models.gson.Message
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
    val dataContext =
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
                    // TODO mb need to send Message as parameter
                    Log.d("Client message", "${serverSocket.remoteAddress}:  ${data}")
                    data = ""
                }

            }
        }
    }

    fun sendData(dataToSend: String) {
        while (data != "") {
            continue
        }
        data = dataToSend
        Log.d("Client message sender", "data accepted= $dataToSend \n and written= $data")
    }


}
//    suspend fun sendMessage(message: String){
//        output.writeStringUtf8("hello server!")
//    }
//    suspend fun acceptMessage(): String? {
//        return(input.readUTF8Line())
//    }

