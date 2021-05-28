package com.example.unocompose.models.network

import android.util.Log
import com.example.unocompose.models.GameData
import com.example.unocompose.models.TAG
import com.example.unocompose.models.cards.Card
import com.example.unocompose.models.cards.DeckOfCards
import com.example.unocompose.models.gson.*
import com.google.gson.Gson
import io.ktor.network.selector.*
import io.ktor.network.sockets.*

import io.ktor.utils.io.*
import kotlinx.coroutines.*
import java.net.Inet4Address
import java.util.concurrent.Executors

//TODO remove string and network address kostyil'

class ServerConnection(
    private val port: Int,
    private val onReceive: (Message) -> Unit
) {

    val gson = Gson()

    /* For Broadcasting */
    val coroutineContext =
        Executors.newSingleThreadExecutor().asCoroutineDispatcher() + CoroutineName("Client data")
    var isSentCounter = 0


    var clientCount = 0
    val clientList = mutableListOf<String>()
    val lobbyList = mutableListOf<User>()

    var gameStarted = GameData.gameStarted

    var message = Message("", "", Protocol.NONE)
    var broadcastMessage = Message("", "", Protocol.NONE)


    val deckOfCards = DeckOfCards()

    fun initGame() {
        while (clientCount != GameData.currentConnectedPlayers) {
            continue
        }
        deckOfCards.createDeck()
        var lastPlayedCard: Card
        do {
            lastPlayedCard = deckOfCards.getRandomCard()
        } while (lastPlayedCard.color == "wild")
        val jsonString = gson.toJson(lastPlayedCard)

        sendBroadcastMessage(
            Message(
                ip = "ANY",
                data = jsonString,
                protocol = Protocol.INITIAL_BOARD_STATE
            )
        )

    }

    suspend fun startServer() {
        Log.d(TAG, "Creating Socket")
        val server = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().bind(port = port)
        Log.d(TAG, "Socket created, ip = ${server.localAddress}")
        lobbyList.add(User(ip = Inet4Address.getLocalHost().hostAddress, GameData.myName))


        while (clientCount < 4 && !gameStarted) {
            val clientSocket = server.accept()
            clientList.add(clientSocket.remoteAddress.toString())
            clientCount++

            GlobalScope.launch {
                Log.d("Server", "Socket accepted: ${clientSocket.remoteAddress}")

                val input = clientSocket.openReadChannel()
                val output = clientSocket.openWriteChannel(autoFlush = true)
                // TODO notify all users broadcastMessage =
                val userName = input.readUTF8Line()
                if (userName == null) {
                    clientSocket.close()
                } else {
                    lobbyList.add(
                        User(
                            name = userName,
                            ip = clientSocket.remoteAddress.toString()
                        )
                    )
                    val jsonString = gson.toJson(LobbyUsers(lobbyList))
                    sendBroadcastMessage(Message(
                        ip = "ANY",
                        protocol = Protocol.LIST_OF_PLAYERS,
                        data = jsonString
                    ))
                }

                GlobalScope.launch {
                    try {
                        while (true) {
                            val receivedMessage = input.readUTF8Line()
                            Log.d(
                                "Server message listener",
                                "${clientSocket.remoteAddress}: $receivedMessage"
                            )
                            if (receivedMessage == null) {
                                clientSocket.close()
                            } else {
                                val data = gson.fromJson(receivedMessage, Message::class.java)
                                onReceive(data)
                            }


                            //TODO message handler
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                        clientSocket.close()

                    }
                }
                GlobalScope.launch {
                    while (true) {
                        if (message.ip == clientSocket.remoteAddress.toString()) {
                            output.writeStringUtf8(message.data)
                            output.writeStringUtf8("\n")
                            clearMessage()
                            // TODO mb need to send Message as parameter
                            Log.d(
                                "Server message writer",
                                "${clientSocket.remoteAddress}:  ${message.data}"
                            )
                        }
                    }
                }
                GlobalScope.launch {
                    while (true) {
                        if (broadcastMessage.ip == "ANY") {
                            val jsonString = gson.toJson(broadcastMessage)
                            output.writeStringUtf8(jsonString)
                            output.writeStringUtf8("\n")
                            Log.d(
                                "Server message writer",
                                "sent ${clientSocket.remoteAddress}:  ${broadcastMessage}"
                            )
                            runBlocking {
                                withContext(coroutineContext) {
                                    clientCount++
                                    if (clientCount == lobbyList.size) {
                                        clientCount = 0
                                        clearBroadcastMessage()
                                    }
                                }
                            }



                        }
                    }
                }
            }

        }

    }

    fun serverMessageHandler() {

    }

    private fun clearMessage() {
        message = Message("", "", Protocol.NONE)
    }

    private fun clearBroadcastMessage() {
        broadcastMessage = Message("", "", Protocol.NONE)
    }

    fun sendMessage(dataToSend: Message) {
        while (message.ip != "") {
            continue
        }
        message = dataToSend
    }

    fun sendBroadcastMessage(dataToSend: Message) {
        while (broadcastMessage.ip != "") {
            continue
        }
        onReceive(dataToSend)
        broadcastMessage = dataToSend

    }

    private fun saveCounter() {
        GameData.currentConnectedPlayers = lobbyList.size
    }

}