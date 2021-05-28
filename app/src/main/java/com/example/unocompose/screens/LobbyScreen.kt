package com.example.unocompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.example.unocompose.models.gson.Message
import com.example.unocompose.ui.theme.*
import com.example.unocompose.viewmodels.LobbyScreenViewModel


@Composable
fun LobbyScreen(
    navController: NavController,
    isHost: Boolean,
    viewModel: LobbyScreenViewModel = hiltNavGraphViewModel()
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
                    top.linkTo(parent.top, margin = 30.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                }

            }

            ConstraintLayout(
                constraints, modifier = Modifier
                    .fillMaxSize()
            ) {

                /*UserList*/
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxHeight()
//                        .background(cardCyan)
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
                Button(onClick = { viewModel.send("nameInit") }) { }

                /*Settings*/
                if (isHost) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
//                            .background(cardOrange)
                            .layoutId("settings")
                    ) {
                        Column() {
                            StartButton(navController = navController)
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
            .background(bgPrimary2, RoundedCornerShape(20.dp))
    ) {
        Column() {

            Text(
                text = name,
                style = Typography.h3,
                color = cardPink,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp)
            )
        }
    }
}

@Composable
fun StartButton(
    navController: NavController,
    viewModel: LobbyScreenViewModel = hiltNavGraphViewModel()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(cardPink)
            .wrapContentHeight()
            .clickable {
                viewModel.unregister()
                navController.navigate("gameScreen") {

                    popUpTo(route = "mainScreen") {}
                }

            }
    ) {
        Text(
            text = "Start",
            style = Typography.h1,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
        )
    }
}
