package com.example.unocompose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import com.example.unocompose.ui.theme.Shapes


@Composable
fun JetpackComposeUnoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = colors,
        typography = UnoTypography,
        shapes = Shapes,
        content = content
    )
}

