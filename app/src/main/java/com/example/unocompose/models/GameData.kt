package com.example.unocompose.models

import java.net.InetAddress

object GameData {
    var myName = ""
    var isServer = false
    var currentConnectedPlayers = 2
    lateinit var currentServer: InetAddress
    var gameStarted = false
}