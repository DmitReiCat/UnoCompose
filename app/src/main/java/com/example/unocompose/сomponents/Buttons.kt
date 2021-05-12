package com.example.unocompose.сomponents

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.unocompose.ui.theme.Typography
import com.example.unocompose.ui.theme.cardBlue
import com.example.unocompose.ui.theme.cardRed


@Composable
fun NavButton(navController: NavController, text: String, isMainButton: Boolean, onClickDestination: String) {
    val textStyle = if (isMainButton) Typography.h1
    else Typography.h2

    val boxColor = if (isMainButton) cardRed
    else cardBlue

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(boxColor)
            .wrapContentHeight()
            .clickable { navController.navigate(onClickDestination) }
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
        )
    }
}


