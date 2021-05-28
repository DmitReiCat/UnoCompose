package com.example.unocompose.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unocompose.models.GameData

class MainScreenViewModel: ViewModel() {

    private var userName = ""
    val userNameState = mutableStateOf(userName)
    val isDialogVisible = mutableStateOf(true)


    fun changeName(name: String){
        userName = name
        GameData.myName = name

    }
}