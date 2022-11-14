package com.example.delegadosapp.vista.publications

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.R
import com.example.delegadosapp.controlador.AuxFunctions.showMessage
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PublicationsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_publications)

        val rol = intent.getIntExtra("rol", 0)
        showMessage(this, "rol = "+rol)

        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val adapter = PostAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        //Si el rol es invitado-0 o usuario-1, no se muestra el botón de añadir
        val fab = findViewById<FloatingActionButton>(R.id.btn_addPublication)
        if(rol == 0 || rol == 1){
            fab.visibility = View.GONE
        }else{
            fab.setOnClickListener {
                val intent = Intent(this, AddNewPublicationActivity::class.java)
                startActivity(intent)
            }
        }
    }
}