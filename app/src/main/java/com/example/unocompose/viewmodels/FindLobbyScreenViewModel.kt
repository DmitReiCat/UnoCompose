package com.example.unocompose.viewmodels

import android.net.nsd.NsdManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unocompose.models.gson.Message
import com.example.unocompose.models.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.Inet4Address
import java.net.InetAddress

class FindLobbyScreenViewModel: ViewModel() {

    private val lobbyList = mutableListOf<ScanResult>()
    val lobbyListState = mutableStateOf<List<ScanResult>>(listOf())

    private fun updateLobbyList() {
        lobbyListState.value = lobbyList.toList()
    }

    suspend fun openOnNetwork(nsdManager: NsdManager) {
        NSDHost(nsdManager).registerService()
    }

    suspend fun connectToLobby(ip: InetAddress){
        GlobalScope.launch {
            ClientConnection.connect(ip)
        }
    }

    suspend fun findLobby(nsdManager: NsdManager) {
        NSDClient(nsdManager){
            lobbyList.add(it)
            updateLobbyList()
            Log.d("LobbyAddingEntry", "Entry added $lobbyList ${it.ipAddress.toString().removePrefix("/")}")
        }.startDiscovery()

    }



}