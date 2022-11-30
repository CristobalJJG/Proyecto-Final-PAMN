package com.example.delegadosapp.vista.publications

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.Publications.PostAdapter
import com.example.delegadosapp.R
import com.example.delegadosapp.AuxFunctions.showMessage
import com.example.delegadosapp.MyCallback
import com.example.delegadosapp.modelo.Noticias
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.login_register.RegisterActivity
import com.example.delegadosapp.vista.login_register.User
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PublicationsActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var uid: String
    private lateinit var titles: Array<String>
    private lateinit var descriptions: Array<String>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_publications)

        //Prueba Orden
        val noticias = Noticias()


        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            this.email = user.email.toString()
            this.uid = user.uid
        }

        //fetchData(this.email)
        Log.d("l-49", User.toString())

        //showMessage(this, "email: " + email + "\n" + "uid: " + uid)

        val recyclerView = findViewById<RecyclerView>(R.id.rv)

        noticias.datosNoticias(object : MyCallback {
            override fun onCallback(value: Array<String>?, value1: Array<String>?) {
                if (value != null) {
                    titles = value
                    Log.d("TAG", value.toString());
                }
                if (value1 != null) {
                    descriptions = value1
                    Log.d("TAG", value1.toString());
                }
                val images = arrayOf(
                    R.drawable.default_picture,
                    R.drawable.default_picture,
                    null,
                    null,
                    R.drawable.default_picture,
                    null
                )
                val adapter = PostAdapter(titles, descriptions, images)
                recyclerView.adapter = adapter

            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)


        //Si el rol es invitado-0 o usuario-1, no se muestra el botón de añadir
        val fab = findViewById<FloatingActionButton>(R.id.btn_addPublication)
        if (User.getRol() == 0 || User.getRol() == 1) {
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

                if(User.getRol() == 0) modalInvite(view)
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
            if(User.getRol() == 1) view.findViewById<TextView>(R.id.txt_modalCargo).text = "Alumno"
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

            if(User.getRol()==2){
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