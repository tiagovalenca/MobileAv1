package com.example.avaliacao1

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FruitData : ViewModel() {
    var fruitsList = MutableLiveData<ArrayList<FruitModel>>()
    var newlist = arrayListOf<FruitModel>()
    var wasPopulatedOnce = false
    var filters = -1

    internal fun getFruitList(): MutableLiveData<List<FruitModel>> {
        if (fruitsList == null) {
            fruitsList = MutableLiveData()
        }
        return fruitsList as MutableLiveData<List<FruitModel>>
    }

    internal fun orderList(){
        when (filters) {
            0 -> {
                fruitsList.value=newlist
            }
            1 -> {
                var hideRepetition = newlist.distinctBy { it.nome?.toLowerCase() }
                fruitsList.value=ArrayList(hideRepetition)
            }
            2 -> {
                var sortedList = newlist.sortedBy { it.nome?.toLowerCase() }
                fruitsList.value=ArrayList(sortedList)
            }
            3 -> {
                var sortedAndHiddenList = newlist.sortedBy { it.nome?.toLowerCase() }
                sortedAndHiddenList = sortedAndHiddenList.distinctBy { it.nome?.toLowerCase() }
                fruitsList.value=ArrayList(sortedAndHiddenList)
            }
            else -> {
                fruitsList.value=newlist
            }
        }
    }

    fun add(fruit: FruitModel){
        newlist.add(fruit)
        orderList()
    }

    fun remove(fruit: FruitModel){
        newlist.remove(fruit)
        orderList()
    }

}