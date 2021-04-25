package com.example.avaliacao1

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var viewModel: FruitData
    private lateinit var recyclerView: RecyclerView
    private lateinit var selectedFruit: FruitModel
    private val SECOND_ACTIVITY_REQUEST_CODE = 0
    private val DETAILS_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar)).apply {
            title = "Lista de Frutas"
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        }

        layoutManager = LinearLayoutManager(applicationContext)

        recyclerView = findViewById(R.id.recyclerView)
        val factory = ViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(FruitData::class.java)

        initialiseAdapter()

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent = Intent(this, AddFruit::class.java)
            startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
        }
    }

    private fun initialiseAdapter(){
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        prepareFruitData()
        observeData()
    }

    fun observeData(){
        viewModel.fruitsList.observe(this, Observer {
            Log.i("data", it.toString())
            recyclerView.adapter = FruitAdapter(viewModel, it, this) { fruit: FruitModel ->
                adapterOnClick(
                        fruit
                )
            }
            recyclerView.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        })
    }

    private fun adapterOnClick(fruit: FruitModel) {
        val intent = Intent(this, FruitDetails()::class.java)
        selectedFruit = fruit
        intent.putExtra("fruit", fruit)
        this.startActivityForResult(intent, DETAILS_CODE)
    }

    private fun prepareFruitData() {
        if (viewModel.newlist.isEmpty() and !viewModel.wasPopulatedOnce) {
            var apple = FruitModel(
                    "Maçã",
                    "As maçãs contêm poderosos nutrientes que estimulam o sistema imunológico. Possuem fibras que ajudam a transportar resíduos para fora do corpo. Além disso, a fruta é fonte de vitamina C que contribui para que o organismo desenvolva resistência.",
                    Uri.parse("android.resource://com.example.avaliacao1/" + R.drawable.apple)
            )
            viewModel.add(apple)
            var banana = FruitModel(
                    "Banana",
                    "A banana é um alimento prático, saudável e altamente nutritivo. Por ser rica em vitaminas do complexo B – são quatro presentes na banana: B1, B2, B6 e B12 – essa fruta auxilia diretamente o sistema nervoso.",
                    Uri.parse("android.resource://com.example.avaliacao1/" + R.drawable.banana)
            )
            viewModel.add(banana)
            var orange = FruitModel(
                    "Laranja",
                    "Os flavonoides presentes na fruta limitam a absorção do colesterol no intestino. A laranja também é rica em vitamina C, flavonoides, betacaroteno e fibras, por isso é ideal para combater o colesterol ruim (LDL).",
                    Uri.parse("android.resource://com.example.avaliacao1/" + R.drawable.oranges)
            )
            viewModel.add(orange)
            var strawberry = FruitModel(
                    "Morango",
                    "O morango é uma fruta rica em antioxidantes, como antocianinas e o ácido elágico, que conferem outros benefícios para a saúde, tias como combater o envelhecimento da pele, ajudar a prevenir doenças cardiovasculares, melhorar a capacidade mental, prevenir o câncer e ajudar a combater inflamações.",
                    Uri.parse("android.resource://com.example.avaliacao1/" + R.drawable.strawberry)
            )
            viewModel.add(strawberry)
            viewModel.wasPopulatedOnce = true
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_filter -> {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Escolha seu filtro")

            val filters = arrayOf("Com repetição + Ordem de Inserção", "Sem Repetição + Ordem de Inserção",
                                    "Com repetição + Ordem Alfabética", "Sem Repetição + Ordem Alfabética")

            var preFilters = viewModel.filters

            builder.setSingleChoiceItems(filters, preFilters,
                    DialogInterface.OnClickListener { dialog, which ->
                        preFilters = which
                    })

            builder.setPositiveButton("Filtrar", DialogInterface.OnClickListener { dialog, id ->
                when (preFilters) {
                    0 -> {
                        viewModel.filters = preFilters
                        viewModel.orderList()
                        recyclerView.adapter?.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                    1 -> {
                        viewModel.filters = preFilters
                        viewModel.orderList()
                        recyclerView.adapter?.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                    2 -> {
                        viewModel.filters = preFilters
                        viewModel.orderList()
                        recyclerView.adapter?.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                    3 -> {
                        viewModel.filters = preFilters
                        viewModel.orderList()
                        recyclerView.adapter?.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                    else -> {
                        Toast.makeText(this, "Escolha um Filtro", Toast.LENGTH_SHORT).show()
                    }
                }
            })

            builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

            builder.show()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val fruit = data!!.getParcelableExtra<FruitModel>("fruit")
                viewModel.add(fruit)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }

        if (requestCode == DETAILS_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.remove(selectedFruit)
            }
        }

    }

}
