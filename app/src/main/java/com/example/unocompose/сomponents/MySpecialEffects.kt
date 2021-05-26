package com.example.unocompose.сomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.unocompose.models.cards.Card
import com.example.unocompose.ui.theme.*
import com.example.unocompose.viewmodels.GameScreenViewModel

@Composable
fun ChoseWildColor(
    isPlus4: Boolean
) {
    Box(
        modifier = Modifier
            .clip(shape = CircleShape)
    ){
     Column(

     ) {
         Row() {
             ColorPicker(color = cardCyan, isPlus4 = isPlus4)
             Spacer(modifier = Modifier.size(5.dp))
             ColorPicker(color = cardOrange, isPlus4 = isPlus4)
         }
         Spacer(modifier = Modifier.size(5.dp))
         Row() {
             ColorPicker(color = cardPurple, isPlus4 = isPlus4)
             Spacer(modifier = Modifier.size(5.dp))
             ColorPicker(color = cardPink, isPlus4 = isPlus4)
         }
     }   
    }
}

@Composable
fun ColorPicker(
    color: Color,
    isPlus4: Boolean,
    viewModel: GameScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Box (
        modifier = Modifier
            .size(size = 100.dp)
            .background(color = color)
            .clickable {
                viewModel.mySpecialEffect.value = ""
                val renewColor = when (color) {
                    cardPink -> "pink"
                    cardCyan -> "cyan"
                    cardOrange -> "orange"
                    else -> "purple"
                }
                if (isPlus4) {
                    viewModel.changeLastPlayedCard(Card("wild", "plus4_$renewColor"))
                } else {
                    viewModel.changeLastPlayedCard(Card("wild", "common_$renewColor"))
                }
            }
    ){
    }
}