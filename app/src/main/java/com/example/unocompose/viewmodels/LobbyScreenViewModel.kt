package com.example.unocompose.viewmodels

import android.net.nsd.NsdManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unocompose.models.network.NSDClient
import com.example.unocompose.models.network.NSDHost
import com.example.unocompose.models.network.ScanResult

class LobbyScreenViewModel: ViewModel() {


    val userData = mutableListOf<ScanResult>()
    val userList = mutableStateOf<List<String>>(listOf())

    fun updateUserList() {
        for (data in userData) {
            userList.value += data.name
        }
    }

    suspend fun openOnNetwork(nsdManager: NsdManager) {
        NSDHost(nsdManager).registerService()
    }

    suspend fun findLobby(nsdManager: NsdManager) {
        NSDClient(nsdManager){  userData.add(it)  }.startDiscovery()
        updateUserList()
    }
}