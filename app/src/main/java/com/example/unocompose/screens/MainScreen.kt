package com.example.unocompose.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.unocompose.R
import com.example.unocompose.сomponents.*
import com.example.unocompose.ui.theme.*
import com.example.unocompose.viewmodels.MainScreenViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController


val padding = 10.dp

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val systemUiController = rememberSystemUiController()

    Surface(
        color = bgPrimary,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            val dissmiss by remember { viewModel.isDialogVisible }
            if (dissmiss) { EnterYourNamePopUp() }
            val constraints = ConstraintSet {
                val logo = createRefFor("logo")
                val userMenu = createRefFor("userMenu")
                constrain(userMenu) {
                    end.linkTo(parent.end, 30.dp)
                    start.linkTo(logo.end, 40.dp)
                }
                constrain(logo) {
                    start.linkTo(parent.start, 30.dp)
                    end.linkTo(userMenu.start, 40.dp)
                    bottom.linkTo(parent.bottom, 30.dp)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                }
            }
            NameField(hint = "Enter your name")

            ConstraintLayout(constraints, modifier = Modifier
                .fillMaxSize()
                /*.background(cardWhite)*/) {

                /*Logo*/
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .layoutId("logo")
                        /*.background(bgSecondary)*/
                ) { 
                    LogoUno()
                }

                /*User Menu*/
                Box(modifier = Modifier
                    .layoutId("userMenu")
                    /*.background(cardGreen)*/
                ) {
                    UserMenu(navController = navController)
                }
            }
        }
    }
}


@Composable
fun EnterYourNamePopUp(
    viewModel: MainScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    Dialog(
        onDismissRequest = { viewModel.isDialogVisible.value = false },
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(shape = RoundedCornerShape(20.dp), color = bgPrimary2)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    textAlign = TextAlign.Center,
                    text = "Welcome to UNO!",
                    style = Typography.h4,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                )
                Spacer(modifier = Modifier.size(30.dp))
                NameField(hint = "Enter yor name here")
                Spacer(modifier = Modifier.size(10.dp))

            }
        }
    }
}

@Composable
fun NameField(
    hint: String,
    viewModel: MainScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var text by remember { viewModel.userNameState }
    var isHintDisplayed by remember { mutableStateOf(hint != "") }
    Box(modifier = Modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                viewModel.changeName(it)
                Log.d("Name", viewModel.userNameState.value)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = Typography.h3,
            modifier = Modifier
                .background(Color.Transparent, CircleShape)
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .onFocusChanged {
                    isHintDisplayed = it != FocusState.Active && text.isEmpty()
                }
                .wrapContentSize()
        )
        if (isHintDisplayed && text.isEmpty()) {
            Text(
                text = hint,
                style = Typography.h3,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
            )
        }

    }
}



@Composable
fun LogoUno() {
    val painter = painterResource(id = R.drawable.uno_logo)
    Image(painter = painter, contentDescription = "")
}

@Composable
fun UserMenu(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavButton(
            navController = navController,
            text = "Create",
            isMainButton = true,
            onClickDestination = "lobbyScreen",
        )
        Spacer(modifier = Modifier.padding(padding))
        NavButton(
            navController = navController,
            text = "Find",
            isMainButton = false,
            onClickDestination = "findLobbyScreen",
        )

    }
}
//fun ConfirmButton(text: String) {
//    val textStyle = Typography.h2
//
//    val boxColor = if (isMainButton) cardRed
//    else cardBlue
//
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .clip(RoundedCornerShape(20.dp))
//            .background(boxColor)
//            .wrapContentHeight()
//            .clickable {  }
//    ) {
//        Text(
//            text = text,
//            style = textStyle,
//            modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
//        )
//    }
//}

