package com.example.unocompose.viewmodels

import android.content.Context
import android.net.nsd.NsdManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.unocompose.models.NSDClient
import com.example.unocompose.models.NSDHost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class LobbyScreenViewModel(nsdManager: NsdManager): ViewModel() {

    private val mNsdManager = nsdManager

    val userList = mutableStateOf<List<String>>(listOf("Amy", "Lily"))

    suspend fun openOnNetwork() {
        NSDHost(mNsdManager).registerService()
    }

    suspend fun findLobby() {
        NSDClient(mNsdManager).startDiscovery()
    }




}