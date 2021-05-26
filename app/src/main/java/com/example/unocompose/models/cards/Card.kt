package com.example.unocompose.models.cards

import android.util.Log
import java.lang.StringBuilder

data class Card(val color: String, val type: String) {
    init {
        Log.d("CARDCLASS" ,"$color $type")
    }
    val drawableName = StringBuilder().append(color, "_", type).toString()


}
