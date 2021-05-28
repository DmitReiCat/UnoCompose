package com.example.unocompose.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unocompose.models.GameData
import com.example.unocompose.models.cards.Card
import com.example.unocompose.models.cards.DeckOfCards
import com.example.unocompose.models.gson.LobbyUsers
import com.example.unocompose.models.gson.Message
import com.example.unocompose.models.gson.Protocol
import com.example.unocompose.models.gson.User
import com.example.unocompose.models.network.ClientConnection
import com.example.unocompose.models.network.MessageHandler
import com.example.unocompose.models.network.ServerConnection
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameScreenViewModel: ViewModel() {
    val userListState = mutableStateOf<List<User>>(listOf<User>(User(ip = "", name = GameData.myName)))

    private val myCardsList = mutableListOf<Card>()
    private var lastPlayedCard = Card("pink","skip")
    private lateinit var deckOfCards: DeckOfCards

    val gson = Gson()
    private val isServer = GameData.isServer

    private val server = ServerConnection(25557){
        onReceiveHandler(it)
    }
    private val client = ClientConnection(25557) {
        val jsonString = it
        val message = gson.fromJson(
            jsonString, Message::class.java
        )
        onReceiveHandler(message)
    }

    private fun onReceiveHandler(message: Message){
        when (message.protocol) {
            Protocol.LIST_OF_PLAYERS -> {
                val data = gson.fromJson(message.data, LobbyUsers::class.java)
                updateUserList(data)
                Log.d("Lobby ViewModel", data.toString())
            }
            Protocol.INITIAL_BOARD_STATE -> {
                val data = gson.fromJson(message.data, Card::class.java)
                Log.d("Deserialised", "${data.toString()}")
                changeLastPlayedCard(data)

            }

            else -> {}
        }
    }

    private fun updateUserList(data: LobbyUsers) {
        userListState.value = data.userList
    }
    val messageHandler = if (isServer) {
        object : MessageHandler() {
            override fun send(message: Message) {
                if (message.ip == "ANY") server.sendBroadcastMessage(message)
                else server.sendMessage(message)
            }

            override fun getCard() {
                TODO("Not yet implemented")
            }

            override fun initialise() {
                TODO("Not yet implemented")
            }
        }
    } else {
        object : MessageHandler() {
            override fun send(message: Message) {
                client.sendData(message)
            }

            override fun getCard() {
                TODO("Not yet implemented")
            }

            override fun initialise() {
                TODO("Not yet implemented")
            }
        }
    }


//    val lastPlayedCards: Queue<String> = LinkedList<String>(listOf("orange_3", "purple_reverse", "pink_skip"))
//    val lastPlayedCardsState = mutableStateOf(lastPlayedCards)



    val lastPlayedCardState = mutableStateOf("unknown")
    val myCardsState = mutableStateOf(listOf<String>())

    val isUnoVisible = mutableStateOf(false)
    val cardCounter = mutableStateOf(myCardsList.size)
    val specialEffectOnMe = mutableStateOf("")
    val mySpecialEffect = mutableStateOf("")

    init {
        if (isServer) {
            GlobalScope.launch {
                server.startServer()
            }
            GlobalScope.launch {
                server.initGame()
            }
        }
    }

    fun changeLastPlayedCard(card: Card) {
        lastPlayedCard = card
        lastPlayedCardState.value = lastPlayedCard.drawableName
    }

    private fun removeFromList(index: Int){
//        addToQueue(myCardsList[index])
        changeLastPlayedCard(myCardsList[index])
        deckOfCards.returnCard(myCardsList[index])
        myCardsList.removeAt(index)
        myCardsState.value = myCardsList.map { it.drawableName }.toList()
        cardCounter.value -= 1
        isUnoVisible.value = cardCounter.value == 2
    }

    private fun addToList(name: Card) {
        myCardsList.add(name)
        myCardsList.sortBy { card ->
            card.color
        }
        myCardsState.value = myCardsList.map { it.drawableName }.toList()
        cardCounter.value += 1
    }

//    fun addToQueue(name: String) {
//        lastPlayedCards.remove()
//        lastPlayedCards.add(name)
//        lastPlayedCardsState.value = lastPlayedCards
//    }


    fun makeMoveWithCard(index: Int) {
        if (
            myCardsList[index].type == lastPlayedCard.type ||
            myCardsList[index].color == lastPlayedCard.color ||
            myCardsList[index].color == "wild"||
            lastPlayedCard.color == "wild" &&
            myCardsList[index].color == lastPlayedCard.type.split("_")[1]
        ) {
            //TODO Special effects
            removeFromList(index)
        }
    }



}