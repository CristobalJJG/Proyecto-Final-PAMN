package com.example.delegadosapp.vista.publications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.Publications.PostAdapter
import com.example.delegadosapp.R
import com.example.delegadosapp.controlador.AuxFunctions.showMessage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.delegadosapp.databinding.ActivityPublicationsBinding
import com.example.delegadosapp.vista.menu.AlumnoMenu
import com.example.delegadosapp.vista.menu.DelegadoMenu
import com.example.delegadosapp.vista.menu.InvitadoMenu

class PublicationsActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var uid: String
    private var rol: Int = 0

    private lateinit var binding: ActivityPublicationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_publications)

        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            this.email = user.email.toString()
            this.uid = user.uid
        }

        //showMessage(this, "email: " + email + "\n" + "uid: " + uid)


        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val titles = arrayOf(
            "Game fest", "Asadero GII/GICD", "Curso de Git",
            "Hola1", "Hola2", "Hola3"
        )
        val descriptions = arrayOf(
            "Pretende ser un lugar cordial, donde presumir de dotes videojugabilísticas a nivel usuario avanzado.",
            "Pretende ser una concurrecia de personas con intención de socializar, algo que, por lo general, 1 de los 2 programadores de esta aplicación no está acostumbrado, y, por lo tanto, no es fácil explicar como se desarrolla tal actividad",
            "Súper curso impartido por nuestra tan querida profesora MariLola, con el que se pretende obtener los conocimientos básicos de Git para un uso profesional.",
            "Somos los mejores",
            "No sabemos como se hace nada",
            "Tenemos minima capacidad para hacer una aplicacion"
        )
        val images = arrayOf(
            R.drawable.default_picture,
            R.drawable.default_picture,
            null,
            null,
            R.drawable.default_picture,
            null
        )
        val adapter = PostAdapter(titles, descriptions, images)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        //Si el rol es invitado-0 o usuario-1, no se muestra el botón de añadir
        val fab = findViewById<FloatingActionButton>(R.id.btn_addPublication)
        if (rol == 0 || rol == 1) {
            fab.visibility = View.GONE
        } else {
            fab.setOnClickListener {
                val intent = Intent(this, AddNewPublicationActivity::class.java)
                startActivity(intent)
            }
        }

        //Forma de abrir el modal del menú para redirigir a todas las pantallas
        findViewById<FloatingActionButton>(R.id.btn_modalMenu)
            .setOnClickListener {
                when(rol){
                    0 -> {
                        val modalBottomSheet = InvitadoMenu()
                        modalBottomSheet.show(supportFragmentManager, InvitadoMenu.TAG)
                    }
                    1-> {
                        val modalBottomSheet = AlumnoMenu()
                        modalBottomSheet.show(supportFragmentManager, AlumnoMenu.TAG)
                    }
                    2 -> {
                        val modalBottomSheet = DelegadoMenu()
                        modalBottomSheet.show(supportFragmentManager, DelegadoMenu.TAG)
                    }
                }
            }
    }
}