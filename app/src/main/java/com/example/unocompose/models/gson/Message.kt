package com.example.unocompose.models.gson

data class Message(
    val ip: String,
    val data: String
) {
    constructor() : this(ip = "", data = "")
}