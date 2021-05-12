 package com.example.unocompose

import android.content.Context
import android.net.nsd.NsdManager

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.unocompose.screens.GameScreen
import com.example.unocompose.screens.LobbyScreen
import com.example.unocompose.screens.MainScreen


 class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        TODO()
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "mainScreen"
            ) {
                composable("mainScreen") {
                    MainScreen(navController = navController)
                }
                composable("createLobbyScreen") {
                    LobbyScreen(navController = navController, isHost = true)
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