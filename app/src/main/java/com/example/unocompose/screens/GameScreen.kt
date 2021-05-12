package com.example.unocompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.unocompose.R
import com.example.unocompose.ui.theme.cardBlue
import com.example.unocompose.ui.theme.cardOrange


@Composable
fun GameScreen(navController: NavController){
    val painter = painterResource(id = R.drawable.green_card_one)
    val visibleList by remember { mutableStateOf(listOf("green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green", "green")) }
    var offset = 0.dp
    Box(
        Modifier
            .border(width = 1.dp, color = cardBlue)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy((-30).dp),
            modifier = Modifier
                .border(width = 3.dp, color = cardOrange)
                .width(640.dp)
        ) {
            items(visibleList) {
                Image(
                    contentScale = ContentScale.FillWidth,
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier
                        .width(80.dp)
                        .clickable {}

                )
            }
        }
    }






}