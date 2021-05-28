package com.example.unocompose.models.gson

data class Message(
    var ip: String,
    var data: String,
    var protocol: Protocol,
    var serverRule: Protocol = Protocol.NONE
) {
    constructor(serverRule: Protocol) : this(
        ip = "",
        data = "",
        protocol = serverRule,
        serverRule = serverRule
    )
}
