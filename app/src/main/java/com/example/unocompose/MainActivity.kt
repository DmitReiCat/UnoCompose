package com.example.unocompose


import android.content.Context
import android.net.nsd.NsdManager
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unocompose.models.GameData
import com.example.unocompose.screens.FindLobbyScreen

import com.example.unocompose.screens.GameScreen
import com.example.unocompose.screens.LobbyScreen
import com.example.unocompose.screens.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        TODO()
        setContent {
            Log.d("NSD", "starting activity")


            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "mainScreen"
            ) {
                composable("mainScreen") {
                    MainScreen(navController = navController)
                }
                composable("lobbyScreen") {

                    GameData.isServer = true
                    LobbyScreen(
                        navController = navController,
                        isHost = true
                    )
                }
                composable("clientLobbyScreen") {
                    GameData.isServer = false
                    LobbyScreen(
                        navController = navController,
                        isHost = false
                    )
                }
                composable("findLobbyScreen") {
                    FindLobbyScreen(navController = navController)
                }
                composable("gameScreen") {
                    GameScreen(navController = navController)
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}