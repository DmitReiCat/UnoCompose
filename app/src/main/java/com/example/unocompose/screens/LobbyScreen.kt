package com.example.unocompose.screens

import android.net.nsd.NsdManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.unocompose.сomponents.ButtonToListen
import com.example.unocompose.сomponents.ButtonToRegister


//TODO()
val playerList = mutableStateOf<List<String>>(mutableListOf("Amy", "Lily"))
//TODO()

@Composable
fun LobbyScreen(
    navController: NavController,
    nsdManager: NsdManager,
    isHost: Boolean,
    viewModel: LobbyScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Surface(
        color = bgPrimary,
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
                    start.linkTo(anchor = parent.start, margin = 20.dp)
                    end.linkTo(anchor = setting.start, margin = 10.dp)
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
                ) {

                /*UserList*/
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(cardCyan)
                        .layoutId("userList")
                ) {
                    val visibleList by remember { viewModel.userListState }
                    LazyRow(
                        Modifier
//                            .border(width = 3.dp, color = cardOrange)
                    ) {
                        items(visibleList) {
                            UserEntry(it)
                        }
                    }
                }

                /*Settings*/
                if (isHost) {
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
                                onClickDestination = "gameScreen",
                                isGame = true
                            )
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
}




@Composable
fun UserEntry(name: String) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .width(150.dp)
            .fillMaxHeight()
            .padding(end = 10.dp)
            .padding(vertical = 30.dp)
            .background(cardPink, RoundedCornerShape(20.dp))
    ) {
        Column() {

            Text(
                text = "Player",
                style = Typography.h3

            )
            Text(
                text = name,
                style = Typography.h3,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp)
            )
        }
    }
}
