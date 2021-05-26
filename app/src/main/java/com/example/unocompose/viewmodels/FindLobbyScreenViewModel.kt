package com.example.unocompose.viewmodels

import android.net.nsd.NsdManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unocompose.models.network.NSDClient
import com.example.unocompose.models.network.NSDHost
import com.example.unocompose.models.network.ScanResult

class FindLobbyScreenViewModel: ViewModel() {

    private val lobbyList = mutableListOf<ScanResult>()
    val lobbyListState = mutableStateOf<List<String>>(listOf())

    private fun updateUserList() {
        for (data in lobbyList) {
            lobbyListState.value += data.name
        }
    }

    suspend fun openOnNetwork(nsdManager: NsdManager) {
        NSDHost(nsdManager).registerService()
    }

    suspend fun findLobby(nsdManager: NsdManager) {
        NSDClient(nsdManager){
            lobbyList.add(it)
            updateUserList()
            Log.d("LobbyAddingEntry", "Entry added $lobbyList ${lobbyListState.value}")
        }.startDiscovery()

    }



}