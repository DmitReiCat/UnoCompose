package com.example.unocompose.screens

import android.net.nsd.NsdManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import kotlinx.coroutines.launch


//TODO()
val playerList = mutableStateOf<List<String>>(mutableListOf("Amy", "Lily"))
//TODO()

@Composable
fun LobbyScreen(
    navController: NavController,
    isHost: Boolean,
    nsdManager: NsdManager
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
                text = "Lobby",
                style = Typography.h1,
            )

            val constraints = ConstraintSet {
                val userList = createRefFor("userList")
                val setting = createRefFor("settings")

                constrain(userList) {
                    start.linkTo(parent.start)
                    end.linkTo(setting.start)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                }
                constrain(setting) {
                    start.linkTo(userList.end)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }

            }

            ConstraintLayout(constraints, modifier = Modifier
                .fillMaxSize()
                .background(cardBlue)) {

                /*UserList*/
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(cardGreen)
                        .layoutId("userList")
                ) {
                    val visibleList by remember { playerList }
                    LazyColumn(
                    ) {
                        items(visibleList) {
                            UserEntry(it)
                        }
                    }
                }

                /*Settings*/
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(cardOrange)
                        .layoutId("settings")
                ) {
                    Column() {
                        NavButton(
                            navController = navController,
                            text = "Start",
                            isMainButton = true,
                            onClickDestination = "gameScreen")
                        ButtonToRegister(nsdManager = nsdManager)
                        ButtonToListen(nsdManager = nsdManager)
                        Button(onClick = { playerList.value += "hello!" }) {
                            
                        }
                    }



                }
            }
        }
    }
}

@Composable
fun ButtonToRegister(
    nsdManager: NsdManager,
    viewModel: LobbyScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val composableScope = rememberCoroutineScope()
    Button(
        onClick = { composableScope.launch {
            viewModel.openOnNetwork(nsdManager)
        } }
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
    viewModel: LobbyScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val composableScope = rememberCoroutineScope()
    Button(
        onClick = { composableScope.launch {
            viewModel.findLobby(nsdManager) }
        }
    ) {
        Text(
            text = "Listen",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
        )
    }
}


@Composable
fun UserEntry(name: String) {
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
