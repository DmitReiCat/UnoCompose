package com.example.unocompose.сomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.unocompose.R
import com.example.unocompose.ui.theme.cardGreen

@Composable
fun PlayerCard() {
    val painter = painterResource(id = R.drawable.cyan_0 )
    Image(
        contentScale = ContentScale.FillWidth,
        painter = painter,
        contentDescription = "",
        modifier = Modifier
            .width(80.dp)
            .clickable {}

    )
}