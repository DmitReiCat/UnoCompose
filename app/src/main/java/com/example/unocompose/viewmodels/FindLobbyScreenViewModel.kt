package com.example.unocompose.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class FindLobbyScreenViewModel: ViewModel() {
    val lobbyList = mutableStateOf<List<String>>(listOf("Amy", "Lily"))



}