package com.example.unocompose.models

import java.net.InetAddress

data class PlayerInfo(
    val ip: InetAddress,
    val name: String,
    val cardCount: Int,
    val effect: String,
    val isTurnToPlay: Boolean
) {
    /*  Lobby Constructor  */
    constructor(ip: InetAddress, name: String) : this(
        ip = ip,
        name = name,
        cardCount = 0,
        effect = "",
        isTurnToPlay = false
        )
}

