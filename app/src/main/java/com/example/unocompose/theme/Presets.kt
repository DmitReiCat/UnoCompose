package com.example.unocompose.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate


@Composable
fun MyButton(navController: NavController, text: String, isMainButton: Boolean) {
    val textStyle = if (isMainButton) UnoTypography.h1
    else UnoTypography.h2

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(cardRed)
            .wrapContentHeight()
            .clickable { navController.navigate("createLobbyScreen") }
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
        )
    }
}