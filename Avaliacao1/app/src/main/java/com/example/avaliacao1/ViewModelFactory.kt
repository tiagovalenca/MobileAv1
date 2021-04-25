package com.example.avaliacao1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FruitData::class.java)){
            return FruitData() as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}