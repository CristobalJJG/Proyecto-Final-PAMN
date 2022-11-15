package com.example.delegadosapp.vista.publications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.Publications.PostAdapter
import com.example.delegadosapp.R

class PublicationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_publications)

        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val titles = arrayOf("Game fest", "Asadero GII/GICD", "Curso de Git",
            "Hola1", "Hola2", "Hola3")
        val descriptions = arrayOf("Pretende ser un lugar cordial, donde presumir de dotes videojugabilísticas a nivel usuario avanzado.",
            "Pretende ser una concurrecia de personas con intención de socializar, algo que, por lo general, 1 de los 2 programadores de esta aplicación no está acostumbrado, y, por lo tanto, no es fácil explicar como se desarrolla tal actividad",
            "Súper curso impartido por nuestra tan querida profesora MariLola, con el que se pretende obtener los conocimientos básicos de Git para un uso profesional.",
            "Somos los mejores",
            "No sabemos como se hace nada",
            "Tenemos minima capacidad para hacer una aplicacion")
        val images = arrayOf(R.drawable.default_picture, R.drawable.default_picture, null, null, R.drawable.default_picture, null)

        val adapter = PostAdapter(titles, descriptions, images, 0)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
