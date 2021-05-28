package com.example.unocompose.viewmodels

import android.net.nsd.NsdManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unocompose.models.GameData
import com.example.unocompose.models.gson.LobbyUsers
import com.example.unocompose.models.gson.Message
import com.example.unocompose.models.gson.Protocol
import com.example.unocompose.models.gson.User
import com.example.unocompose.models.network.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LobbyScreenViewModel @Inject constructor(
    val nsdManager: NsdManager
): ViewModel()  {

    val userListState = mutableStateOf<List<User>>(listOf<User>(User(ip = "", name = GameData.myName)))
    val gson = Gson()
    val connectToGame = mutableStateOf(false)


    private val nsdHost = NSDHost(nsdManager)
    private val server = ServerConnection(25556){
        onReceiveHandler(it)
    }
    private val client = ClientConnection(25556) {
        val jsonString = it
        val message = gson.fromJson(jsonString, Message::class.java)
        onReceiveHandler(message)
    }

    private fun onReceiveHandler(message: Message){
        when (message.protocol) {
            Protocol.LIST_OF_PLAYERS -> {
                val data = gson.fromJson(message.data, LobbyUsers::class.java)
                updateUserList(data)
                Log.d("Lobby ViewModel", data.toString())
            }
            Protocol.START_GAME -> connectToGame.value = true
            Protocol.ON_LIMIT_REACHED -> unregister()
            else -> {}
        }
    }


    init {
        if (GameData.isServer) {
            Log.d("Lobby", "init")
            GlobalScope.launch {
                nsdHost.registerService()
            }
            GlobalScope.launch {
                server.startServer()
            }
        } else {
            GlobalScope.launch {
                client.connect()
            }
        }
    }


    fun tearDownConnection() {

    }


    fun unregister() {
        nsdManager.unregisterService(nsdHost.registrationListener)
    }




    private fun updateUserList(data: LobbyUsers) {
        userListState.value = data.userList
    }

    fun getUserList() {

    }



}