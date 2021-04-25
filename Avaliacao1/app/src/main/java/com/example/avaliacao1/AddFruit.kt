package com.example.avaliacao1

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class AddFruit : AppCompatActivity() {
    lateinit var imageView: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_fruit)

        setSupportActionBar(findViewById(R.id.toolbarAdd))

        supportActionBar?.apply {
            title = "Adicionar Fruta"

            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        imageView = findViewById(R.id.addImageView)
    }

    fun buttonClickSave(view: View) {
        val nomeFruta = findViewById<EditText>(R.id.addNome)
        val stringFruta = nomeFruta.text.toString()

        val beneficioFruta = findViewById<EditText>(R.id.addBeneficios)
        val stringBeneficio = beneficioFruta.text.toString()

        val imagemFruta = imageUri

        val fruit = FruitModel(stringFruta, stringBeneficio, imagemFruta)

        val intent = Intent()
        intent.putExtra("fruit", fruit)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun buttonClickCancel(view: View) {
        val intent = Intent()
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    fun buttonClickGetImage(view: View){
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }
}