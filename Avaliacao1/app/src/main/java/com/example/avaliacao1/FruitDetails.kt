package com.example.avaliacao1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FruitDetails : AppCompatActivity() {
    var imagem: ImageView? = null
    var nome: TextView? = null
    var beneficio: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit_details)

        val i = intent
        val fruit = i.extras!!.getParcelable<FruitModel>("fruit")

        setSupportActionBar(findViewById(R.id.toolbarDetails))

        supportActionBar?.apply {
            title = fruit?.nome

            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        //REFERENCE VIEWS FROM XML
        nome = findViewById<View>(R.id.nomeDetails) as TextView
        beneficio = findViewById<View>(R.id.beneficioDetails) as TextView
        imagem = findViewById<View>(R.id.imageDetails) as ImageView

        //ASSIGN DATA TO THOSE VIEWS
        imagem!!.setImageURI(fruit?.imagem)
        nome!!.text = "Nome :   ${fruit?.nome}"
        beneficio!!.setText("Benefícios : ${fruit?.beneficio}")

        findViewById<Button>(R.id.buttonRemoveFruit).setOnClickListener { view ->
            buttonClickRemove(view)
        }

    }

    private fun buttonClickRemove(view: View) {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}