package com.example.avaliacao1

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView


internal class FruitAdapter(val viewModel: FruitData, val fruitsList: ArrayList<FruitModel>, val context: Context, val clickListener: (FruitModel) -> Unit)  :
    RecyclerView.Adapter<FruitAdapter.MyViewHolder>() {
    private lateinit var imagem: ImageView

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nome: TextView = view.findViewById(R.id.nome)
        var beneficio: TextView = view.findViewById(R.id.beneficio)
        var imagem: ImageView = view.findViewById(R.id.image)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fruit = fruitsList[position]
        holder.nome.text = fruit.nome
        holder.beneficio.text = fruit.beneficio
        holder.imagem.setImageURI(fruit.imagem)
        holder?.itemView?.setOnClickListener { clickListener(fruit) }
    }
    override fun getItemCount(): Int {
        return fruitsList.size
    }

}