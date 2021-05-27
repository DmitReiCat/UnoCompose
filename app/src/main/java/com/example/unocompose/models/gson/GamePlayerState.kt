package com.example.unocompose.models.gson

import com.example.unocompose.models.cards.Card

data class GamePlayerState(
    val ip: String,
    val cardCount: Int,
    val playedCard: Card
)

data class CardRequest(
    val ip: String
)

