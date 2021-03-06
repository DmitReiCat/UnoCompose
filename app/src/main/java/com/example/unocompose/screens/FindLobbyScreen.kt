package com.example.unocompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.unocompose.models.network.ScanResult
import com.example.unocompose.ui.theme.*
import com.example.unocompose.viewmodels.FindLobbyScreenViewModel

import kotlinx.coroutines.launch


//TODO()
val lobbyList = mutableStateOf<List<String>>(listOf("Amy", "Lily"))
//TODO()

@Composable
fun FindLobbyScreen(
    navController: NavController,
    viewModel: FindLobbyScreenViewModel = hiltNavGraphViewModel()
) {
    Surface(
        color = bgPrimary,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            /*Lobby Text*/
            Row() {
                Text(
                    text = "Available lobbies",
                    style = Typography.h1,
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .layoutId("userList")
            ) {
                val visibleList by remember { viewModel.lobbyListState }
                LazyColumn(
                ) {
                    items(visibleList) {
                        LobbyEntry(lobby = it, navController = navController)
                    }
                }
            }
        }
    }
}


@Composable
fun LobbyEntry(
    lobby: ScanResult,
    navController: NavController,
    viewModel: FindLobbyScreenViewModel = hiltNavGraphViewModel()
) {
    val composableScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(cardPurple)
            .clickable(onClick = {
                navController.navigate("clientLobbyScreen")
                viewModel.connectToLobby(lobby.ipAddress)
            })
    ) {
        Text(
            text = lobby.name,
            style = Typography.h1,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)

        )
    }
}
