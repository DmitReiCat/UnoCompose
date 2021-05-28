package com.example.unocompose.сomponents

import android.net.nsd.NsdManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.example.unocompose.models.GameData
import com.example.unocompose.ui.theme.Typography
import com.example.unocompose.ui.theme.cardPurple
import com.example.unocompose.ui.theme.cardPink
import com.example.unocompose.viewmodels.FindLobbyScreenViewModel
import kotlinx.coroutines.launch


@Composable
fun NavButton(
    navController: NavController,
    text: String,
    isMainButton: Boolean,
    onClickDestination: String,
) {
    val textStyle = if (isMainButton) Typography.h1
    else Typography.h2

    val boxColor = if (isMainButton) cardPink
    else cardPurple

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(boxColor)
            .wrapContentHeight()
            .clickable {
                if (GameData.myName.isNotBlank()) {
                    navController.navigate(onClickDestination) {
                    }
                }
            }
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
        )
    }
}


@Composable
fun ButtonToRegister(
    nsdManager: NsdManager
) {
    val composableScope = rememberCoroutineScope()
    Button(
        onClick = {
            composableScope.launch {
//                viewModel.openOnNetwork(nsdManager)
            }
        }
    ) {
        Text(
            text = "Register",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
        )
    }
}

@Composable
fun ButtonToListen(
    nsdManager: NsdManager,
) {
    val composableScope = rememberCoroutineScope()
    Button(
        onClick = {

            composableScope.launch {
//                viewModel.findLobby(nsdManager)
            }


        }
    ) {
        Text(
            text = "Listen",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
        )
    }
}


