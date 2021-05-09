package com.example.unocompose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.unocompose.R

// Set of Material typography styles to start with
val NunitoSans = FontFamily(
        Font(R.font.nunitosans_light, FontWeight.Light),
        Font(R.font.nunitosans_regular, FontWeight.Normal),
        Font(R.font.nunitosans_bold, FontWeight.Medium),
        Font(R.font.nunitosans_extrabold, FontWeight.Bold),
)

val Nunito = FontFamily(
        Font(R.font.nunito_light, FontWeight.Light),
        Font(R.font.nunito_regular, FontWeight.Normal),
        Font(R.font.nunito_extrabold, FontWeight.Bold),
)

val Typography = Typography(
        h1 = TextStyle(
                fontFamily = NunitoSans,
                color = textWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
        ),
        h2 = TextStyle(
                fontFamily = NunitoSans,
                color = textWhite,
                fontWeight = FontWeight.Medium,
                fontSize = 40.sp,
        ),
)