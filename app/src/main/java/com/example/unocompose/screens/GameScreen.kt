package com.example.unocompose.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import com.example.unocompose.R
import com.example.unocompose.ui.theme.*
import com.example.unocompose.viewmodels.GameScreenViewModel


@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    Surface(
        color = cardBlack,
        modifier = Modifier
            .fillMaxSize()
    ){
        val constraints = ConstraintSet {
            val myCards = createRefFor("myCards")
            val unoButton = createRefFor("unoButton")
            val myCounter = createRefFor("myCounter")
            val lastPlayedCard = createRefFor("lastPlayedCard")

            constrain(myCards) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(unoButton) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
            constrain(myCounter) {
                top.linkTo(myCards.top)
                start.linkTo(parent.start)
            }
            constrain(lastPlayedCard) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(myCards.top)
            }

        }

        ConstraintLayout(constraints, modifier = Modifier
            .fillMaxSize()
            .background(cardBlack)) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .layoutId("myCards")
                    .width(400.dp)
            ){  MyCards()  }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .layoutId("unoButton")
                    .padding(vertical = 15.dp, horizontal = 30.dp)
            ){  UnoButton()  }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .layoutId("myCounter")
                    .padding(vertical = 15.dp, horizontal = 30.dp)
            ){  MyCardsCounter()  }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .layoutId("lastPlayedCard")
                    .padding(vertical = 15.dp, horizontal = 30.dp)
            ){  LastPlayedCard()  }

        }








    }
}


@Composable
fun LastPlayedCard(
    viewModel: GameScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val lastCard by remember {  viewModel.lastPlayedCard  }
    val resId = LocalContext.current.resources.getIdentifier(
        lastCard,
        "drawable",
        LocalContext.current.packageName
    )
    Log.d("resId", "$resId, ${LocalContext.current.packageName}")
    val painter = painterResource(id = resId)
    Image(
        contentScale = ContentScale.FillWidth,
        painter = painter,
        contentDescription = "",
        modifier = Modifier
            .width(70.dp)

    )

}

@Composable
fun UnoButton(
    viewModel: GameScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val isVisible by remember {  viewModel.isUnoVisible  }
    
    if (isVisible) {
        val painter = painterResource(id = R.drawable.uno_button_icon)
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .height(100.dp)
                .clickable {

                }
        )
    }
}

@Composable
fun MyCardsCounter(
    viewModel: GameScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val counter by remember {  viewModel.cardCounter  }
    Column() {
        val painter = painterResource(id = R.drawable.counter_icon)
        Image(
            contentScale = ContentScale.FillHeight,
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .height(40.dp)
        )
        Text(text = counter.toString(), style = Typography.h1)

    }

}



@Composable
fun MyCards(
    viewModel: GameScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    //ForSerrver~!
    viewModel.initialiseDeck()
    
    ///
    val visibleList by remember { viewModel.myCardsState }
    LazyRow(
        horizontalArrangement = Arrangement
            .spacedBy((-30).dp),

        ) {
        itemsIndexed(visibleList) { index, name ->
            val resId = LocalContext.current.resources.getIdentifier(
                name,
                "drawable",
                LocalContext.current.packageName
            )
            Log.d("resId", "$resId, ${LocalContext.current.packageName}")
            val painter = painterResource(id = resId)
            Image(
                contentScale = ContentScale.FillWidth,
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .width(80.dp)
                    .clickable {
                        viewModel.onCardClick(index, name)
                        Log.d(
                            "CardList",
                            "${visibleList.toString()} ${viewModel.myCardsList}"
                        )
                    }
            )
        }
    }
}



@Preview
@Composable
fun ComposablePreview() {
}