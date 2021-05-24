package com.example.unocompose.models.cards

import android.util.Log
import java.lang.StringBuilder
import java.util.*

object DeckOfCards {
    var cards = mutableMapOf<Card, Int>()
    private val decksCount = 2
    init {
        Log.d("DeckOfCards", "init")
    }

//    val cardsAvailable = mutableMapOf<Card, Int>(
//        Card("cyan_0") to 2,
//        Card("cyan_1") to 2,
//        Card("cyan_2") to 2,
//        Card("cyan_3") to 2,
//        Card("cyan_4") to 2,
//        Card("cyan_5") to 2,
//        Card("cyan_6") to 2,
//        Card("cyan_7") to 2,
//        Card("cyan_8") to 2,
//        Card("cyan_9") to 2,
//        Card("cyan_0") to 2,
//        Card("cyan_plus2") to 2,
//        Card("cyan_reverse") to 2,
//        Card("cyan_skip") to 2,
//
//        Card("orange_0") to 2,
//        Card("orange_1") to 2,
//        Card("orange_2") to 2,
//        Card("orange_3") to 2,
//        Card("orange_4") to 2,
//        Card("orange_5") to 2,
//        Card("orange_6") to 2,
//        Card("orange_7") to 2,
//        Card("orange_8") to 2,
//        Card("orange_9") to 2,
//        Card("orange_0") to 2,
//        Card("orange_plus2") to 2,
//        Card("orange_reverse") to 2,
//        Card("orange_skip") to 2,
//
//        Card("pink_0") to 2,
//        Card("cyan_1") to 2,
//        Card("cyan_2") to 2,
//        Card("cyan_3") to 2,
//        Card("cyan_4") to 2,
//        Card("cyan_5") to 2,
//        Card("cyan_6") to 2,
//        Card("cyan_7") to 2,
//        Card("cyan_8") to 2,
//        Card("cyan_9") to 2,
//        Card("cyan_0") to 2,
//        Card("cyan_plus2") to 2,
//        Card("cyan_reverse") to 2,
//        Card("cyan_skip") to 2,
//
//        Card("cyan_0") to 2,
//        Card("cyan_1") to 2,
//        Card("cyan_2") to 2,
//        Card("cyan_3") to 2,
//        Card("cyan_4") to 2,
//        Card("cyan_5") to 2,
//        Card("cyan_6") to 2,
//        Card("cyan_7") to 2,
//        Card("cyan_8") to 2,
//        Card("cyan_9") to 2,
//        Card("cyan_0") to 2,
//        Card("cyan_plus2") to 2,
//        Card("cyan_reverse") to 2,
//        Card("cyan_skip") to 2,
//    )

    private val colors = listOf("cyan", "orange","pink", "purple")
    private val types = listOf(
        "0", "1", "2", "3", "4", "5", "6", "7",
        "8", "9", "skip", "reverse", "plus2"
        )
    fun createDeck () {
        for (color in colors) {
            for (type in types) {
                val name = StringBuilder()
                name.append(color, "_", type)
                cards[Card(name.toString())] = decksCount
            }
        }
        Log.d("Deck", cards.toString())
    }


    fun getRandomCard(): Card {
        val random = Random()
        val name = StringBuilder()
        name.append(
            when (Random().nextInt(3)) {
            0 -> "cyan"
            1 -> "orange"
            2 -> "pink"
            else -> "purple"
            },
            "_",
            when (Random().nextInt(12)) {
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
        )
        Log.d("CardGenerationName" ,"${name.toString()}")
        return Card(name.toString())
    }

}

