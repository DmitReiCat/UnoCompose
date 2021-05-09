package com.example.unocompose.uiScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.unocompose.Components.NavButton

import com.example.unocompose.ui.theme.cardBlack
import com.example.unocompose.ui.theme.cardOrange
import com.example.unocompose.ui.theme.cardRed

@Composable
fun Lobbyscreen(
    navController: NavController,
    isHost: Boolean
) {
    Surface(
        color = cardBlack,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row() {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(3f)
                        .background(cardRed)
                ) {

                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .background(cardOrange)
                ) {
                NavButton(
                    navController = navController,
                    text = "Start",
                    isMainButton = true,
                    onClickDestination = "createLobbyScreen")
                }

            }
        }
    }
}