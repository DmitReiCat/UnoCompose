package com.example.unocompose.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unocompose.models.cards.DeckOfCards
import com.example.unocompose.models.cards.Card
import java.util.*

class GameScreenViewModel: ViewModel() {


    val myCardsList = mutableListOf("cyan_0", "cyan_1", "cyan_skip",)
    val myCardsState = mutableStateOf(listOf("cyan_0", "cyan_1", "cyan_skip",))
//    val lastPlayedCards: Queue<String> = LinkedList<String>(listOf("orange_3", "purple_reverse", "pink_skip"))
//    val lastPlayedCardsState = mutableStateOf(lastPlayedCards)
    val lastPlayedCard = mutableStateOf("pink_skip")

    val isUnoVisible = mutableStateOf(true)
    val cardCounter = mutableStateOf(myCardsList.size)


    fun removeFromList(index: Int){
//        addToQueue(myCardsList[index])
        lastPlayedCard.value = myCardsList[index]
        myCardsList.removeAt(index)
        myCardsState.value = myCardsList.toList()
        cardCounter.value -= 1
    }

    fun addToList(name: String) {
        myCardsList.add(name)
        myCardsState.value = myCardsList.toList()
        cardCounter.value += 1
    }

//    fun addToQueue(name: String) {
//        lastPlayedCards.remove()
//        lastPlayedCards.add(name)
//        lastPlayedCardsState.value = lastPlayedCards
//    }


    fun initialiseDeck(){
        DeckOfCards.createDeck()
        for (counter in 0..100) {
            val card = DeckOfCards.getRandomCard()
            Log.d("CardProperties" ,"${card.type} ${card.color}")
        }
    }

    fun onCardClick(index: Int, name: String) {
        if (
            Card(name).type == Card(lastPlayedCard.value).type ||
            Card(name).color == Card(lastPlayedCard.value).color ||
            Card(name).color == "wild"
        ) {
            removeFromList(index)
        }
    }



}