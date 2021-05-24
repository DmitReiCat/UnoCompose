package com.example.unocompose.screens

import android.net.nsd.NsdManager
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.unocompose.сomponents.NavButton
import com.example.unocompose.ui.theme.*
import com.example.unocompose.viewmodels.LobbyScreenViewModel


//TODO()
val lobbyList = mutableStateOf<List<String>>(listOf("Amy", "Lily"))
//TODO()

@Composable
fun FindLobbyScreen(
    navController: NavController,
    nsdManager: NsdManager,
    viewModel: LobbyScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Surface(
        color = cardBlack,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()) {

            /*Lobby Text*/
            Text(
                text = "Available lobbies",
                style = Typography.h1,
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(cardGreen)
                    .layoutId("userList")
            ) {
                val visibleList by remember { viewModel.userList }
                LazyColumn(
                ) {
                    items(visibleList) {
                        UserEntry(it)
                    }
                }
            }
        }
    }
}


@Composable
fun LobbyEntry(name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .background(cardBlue, RoundedCornerShape(20.dp))
    ) {
        Text(
            text = name,
            style = Typography.h1

        )
    }
}
