package com.example.unocompose.models

import android.util.Log
import com.example.unocompose.models.cards.DeckOfCards

class ServerHandler {
    private val deck = DeckOfCards()

    fun initialiseDeck(){
        deck.createDeck()
        for (counter in 0..100) {
            val card = deck.getRandomCard()
//            Log.d("CardProperties" ,"${card.type} ${card.color}")
        }
    }
}