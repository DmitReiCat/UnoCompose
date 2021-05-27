package com.example.unocompose.models.gson

data class User(
    val ip: String,
    val name: String
)

data class LobbyUsers(
    val userList: List<User>
)