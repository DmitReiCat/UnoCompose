package com.example.unocompose.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainScreenViewModel: ViewModel() {

    private var userName = ""
    val userNameState = mutableStateOf(userName)

    fun changeName(name: String){
        userName = name

    }
}