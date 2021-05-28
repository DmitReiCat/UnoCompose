package com.example.unocompose.viewmodels

import android.net.nsd.NsdManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unocompose.models.GameData
import com.example.unocompose.models.PlayerInfo
import com.example.unocompose.models.TAG
import com.example.unocompose.models.gson.Message
import com.example.unocompose.models.network.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LobbyScreenViewModel @Inject constructor(
    val nsdManager: NsdManager
): ViewModel()  {
    private val nsdHost = NSDHost(nsdManager)
    init {
        if (GameData.isServer) {
            Log.d("Lobby", "init")
            viewModelScope.launch {
                nsdHost.registerService()
                ServerConnection.startServer()
            }
        }
    }


    private val isServer = GameData.isServer
    val messageHandler = if (isServer) {
        object : MessageHandler() {
            override fun send (type: String) {

            }

            override fun receive() {
//                return Message("", "")
            }
        }
    } else {
        object : MessageHandler() {
            override fun send (type: String) {
                val message = when (type) {
                    "nameInit" -> Message("", GameData.myName, "nameInit")
                    else -> Message("none", "none", "none")
                }
                ClientConnection.sendData(message)
                Log.d("Created Message", "${message}")
            }

            override fun receive() {
//                return Message("", "")
            }
        }
    }


    fun unregister() {
        nsdManager.unregisterService(nsdHost.registrationListener)
    }


    private val userList = mutableListOf<PlayerInfo>()
    val userListState = mutableStateOf<List<String>>(listOf("Amy", "Amy", "Amy", "Amy"))

    private fun updateUserList() {
        for (data in userList) {
            userListState.value += data.name
        }
    }

    fun getUserList() {

    }

    fun send(type: String) {
        messageHandler.send(type)
    }
    fun receive(data: Message) {
        messageHandler.receive()
    }



}