package com.example.unocompose.models.gson

import java.net.InetAddress

data class Message(
    var ip: String,
    var data: String,
    var type: String
) {
    constructor() : this(ip = "", data = "", type = "")
}