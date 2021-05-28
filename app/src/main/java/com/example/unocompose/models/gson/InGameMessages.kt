package com.example.unocompose.models.gson

import com.example.unocompose.models.cards.Card

data class PlayerState(
    val ip: String,
    val effect: String,
    val cardCount: Int,
    val playedCard: Card,
    val isTurnToPlay: Boolean
)


data class CardRequest(
    val ip: String
)

