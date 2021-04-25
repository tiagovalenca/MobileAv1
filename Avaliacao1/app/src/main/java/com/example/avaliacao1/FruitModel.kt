package com.example.avaliacao1

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

class FruitModel(val nome: String?, val beneficio: String?, val imagem: Uri?) : Parcelable {
    constructor(parcel: Parcel) : this(
        nome = parcel.readString(),
        beneficio = parcel.readString(),
        imagem = parcel.readParcelable(Uri::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(beneficio)
        parcel.writeParcelable(imagem, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FruitModel> {
        override fun createFromParcel(parcel: Parcel): FruitModel {
            return FruitModel(parcel)
        }

        override fun newArray(size: Int): Array<FruitModel?> {
            return arrayOfNulls(size)
        }
    }
}