package com.example.delegadosapp.vista.publications

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.Publications.PostAdapter
import com.example.delegadosapp.R
import com.example.delegadosapp.AuxFunctions.showMessage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.delegadosapp.databinding.ActivityPublicationsBinding
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.login_register.RegisterActivity
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class PublicationsActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var uid: String
    private var rol: Int = 2

    private lateinit var binding: ActivityPublicationsBinding

    @SuppressLint("InflateParams", "MissingInflatedId")
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
                val modal = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.menu_layout, null)

                if(rol == 0) modalInvite(view)
                else modalRegistrado(view)

                modal.setContentView(view)
                modal.show()
            }
        }

        fun modalInvite(view:View){

            view.findViewById<TextView>(R.id.txt_modalUserName).text = "Invitado"
            view.findViewById<TextView>(R.id.txt_modalCargo).visibility = View.GONE

            val btn_login = view.findViewById<Button>(R.id.btn_menuLogin)
            btn_login.visibility = View.VISIBLE
            btn_login.setOnClickListener{ startActivity(Intent(this, LoginActivity::class.java)) }

            val btn_register = view.findViewById<Button>(R.id.btn_menuRegister)
            btn_register.visibility = View.VISIBLE
            btn_register.setOnClickListener{ startActivity(Intent(this, RegisterActivity::class.java)) }
        }

        fun modalRegistrado(view:View){

            view.findViewById<TextView>(R.id.txt_modalUserName).text = "Nombre del usuario"
            if(rol == 1) view.findViewById<TextView>(R.id.txt_modalCargo).text = "Alumno"
            else view.findViewById<TextView>(R.id.txt_modalCargo).text = "Delegado"

            val btn_inicio = view.findViewById<Button>(R.id.btn_menuInicio)
            btn_inicio.visibility = View.VISIBLE
            btn_inicio.setOnClickListener{ startActivity(Intent(this, PublicationsActivity::class.java)) }

            val btn_profile = view.findViewById<Button>(R.id.btn_menuProfile)
            btn_profile.visibility = View.VISIBLE
            btn_profile.setOnClickListener{ startActivity(Intent(this, ProfileActivity::class.java)) }

            val btn_favs = view.findViewById<Button>(R.id.btn_menuFavs)
            btn_favs.visibility = View.VISIBLE
            btn_favs.setOnClickListener{ showMessage(this, "Work In Progress") }

            if(rol==2){
                val btn_meetings = view.findViewById<Button>(R.id.btn_menuMeetings)
                btn_meetings.visibility = View.VISIBLE
                btn_meetings.setOnClickListener{ showMessage(this, "Work In Progress") }
            }
            val btn_listaDelega = view.findViewById<Button>(R.id.btn_menuListDelega)
            btn_listaDelega.visibility = View.VISIBLE
            btn_listaDelega.setOnClickListener{ showMessage(this, "Work In Progress") }

            val btn_logout = view.findViewById<Button>(R.id.btn_menuLogout)
            btn_logout.visibility = View.VISIBLE
            btn_logout.setOnClickListener{ showMessage(this, "Asumamos que has cerrado sesión (Spoiler, WIP)") }
        }
    }