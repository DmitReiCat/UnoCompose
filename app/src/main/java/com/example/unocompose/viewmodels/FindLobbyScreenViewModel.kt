package com.example.unocompose.viewmodels

import android.net.nsd.NsdManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unocompose.models.GameData
import com.example.unocompose.models.gson.Message
import com.example.unocompose.models.network.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.Inet4Address
import java.net.InetAddress
import javax.inject.Inject

@HiltViewModel
class FindLobbyScreenViewModel @Inject constructor(
    val nsdManager: NsdManager
): ViewModel() {

    init {
        viewModelScope.launch {
            findLobby(nsdManager)
        }

    }

    private val lobbyList = mutableListOf<ScanResult>()
    val lobbyListState = mutableStateOf<List<ScanResult>>(listOf())

    private fun updateLobbyList() {
        lobbyListState.value = lobbyList.toList()
    }

    fun connectToLobby(ip: InetAddress){
        GameData.currentServer = ip
    }

    suspend fun findLobby(nsdManager: NsdManager) {
        NSDClient(nsdManager){
            lobbyList.add(it)
            updateLobbyList()
            Log.d("LobbyAddingEntry", "Entry added $lobbyList ${it.ipAddress.toString().removePrefix("/")}")
        }.startDiscovery()

    }



}