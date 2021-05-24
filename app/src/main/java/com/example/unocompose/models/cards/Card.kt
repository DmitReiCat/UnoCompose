package com.example.unocompose.models.cards

import android.util.Log

class Card(private val name: String) {
    init {
        Log.d("CARDCLASS" ,name)
    }

    val split = name.split("_")
    val color = split[0]
    val type = split[1]

}
