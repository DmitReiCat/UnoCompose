package com.example.unocompose.models.cards

import android.util.Log
import java.lang.StringBuilder
import java.util.*

class DeckOfCards {

    var cards = mutableMapOf<Card, Int>()
    private val decksCount = 2

    private val colors = listOf("cyan", "orange", "pink", "purple")
    private val types = listOf(
        "0", "1", "2", "3", "4", "5", "6", "7",
        "8", "9", "skip", "reverse", "plus2"
    )


    fun createDeck() {
        for (color in colors) {
            for (type in types) {
                cards[Card(color, type)] = decksCount
            }
        }
        for (iterations in 0..1) {
            cards[Card("wild", "common")] = decksCount
            cards[Card("wild", "plus4")] = decksCount
        }
        Log.d("Deck", cards.toString())
    }

    fun getRandomCard(): Card {
        var type = ""
        var color = ""
        do {
            color = when (Random().nextInt(5)) {
                0 -> "cyan"
                1 -> "orange"
                2 -> "pink"
                3 -> "purple"
                else ->{
                    Log.d("Generated", "WILD!")
                    "wild"
                }

            }
            type = if (color != "wild") {
                when (Random().nextInt(13)) {
                    0 -> "0"
                    1 -> "1"
                    2 -> "2"
                    3 -> "3"
                    4 -> "4"
                    5 -> "5"
                    6 -> "6"
                    7 -> "7"
                    8 -> "8"
                    9 -> "9"
                    10 -> "skip"
                    11 -> "reverse"
                    else -> "plus2"
                }
            } else {
                when (Random().nextInt(1)) {
                    0 -> "common"
                    else -> "plus4"
                }
            }
            Log.d("DeckRamdomised", "${ Card(color, type) } ${ cards[Card(color,type)] }")
        } while (cards[Card(color,type)]!! == 0)
        cards[Card(color,type)] = cards[Card(color,type)]!! - 1
        return Card(color, type)
    }

    fun returnCard(card: Card){
        Log.d("returning card...", "${ cards[card] }")
        cards[card] = cards[card]!! + 1
        Log.d("card returned", "${ cards[card] }")
    }

}