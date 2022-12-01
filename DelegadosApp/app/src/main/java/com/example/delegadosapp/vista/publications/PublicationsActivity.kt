package com.example.delegadosapp.vista.publications

import android.content.Context
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
import com.example.delegadosapp.modelo.Usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.login_register.RegisterActivity
import com.example.delegadosapp.vista.login_register.User
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore

class PublicationsActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var uid: String
    private lateinit var titles: Array<String>
    private lateinit var descriptions: Array<String>
    private var db = FirebaseFirestore.getInstance()
    private var log_usuatio: Usuario? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_publications)

        //Rescatar los datos de las noticias
        val noticias = Noticias()
        val usuario = Usuario()


        //Información del usuario que esta logeado
        val user = Firebase.auth.currentUser
        user?.let {
         /*   if(user? == null){

        }*/
            // Name, email address, and profile photo Url
            this.email = user.email.toString()
            this.uid = user.uid
        }
        Log.w("Correo:        ", "${email}")

        val recyclerView = findViewById<RecyclerView>(R.id.rv)

        //Llamada a la funcion para rescatar datos
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

            override fun usuarioCallback(actual_usr: Usuario?, contex: Context) {
                TODO("Not yet implemented")
            }
        })

        usuario.fetchData(object : MyCallback {
            override fun onCallback(value: Array<String>?, value1: Array<String>?) {
                TODO("Not yet implemented")
            }

            override fun usuarioCallback(actual_usr: Usuario?, contex: Context) {
                log_usuatio=actual_usr
                //Si el rol es invitado-0 o usuario-1, no se muestra el botón de añadir
                val fab = findViewById<FloatingActionButton>(R.id.btn_addPublication)
                if (actual_usr != null) {
                    if (actual_usr.getRol() == 0 || actual_usr.getRol() == 1) {
                        fab.visibility = View.GONE
                    } else {
                        fab.setOnClickListener {
                            val intent = Intent(contex, AddNewPublicationActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }

                //Forma de abrir el modal del menú para redirigir a todas las pantallas
                findViewById<FloatingActionButton>(R.id.btn_modalMenu)
                    .setOnClickListener {
                        val modal = BottomSheetDialog(contex)
                        val view = layoutInflater.inflate(R.layout.menu_layout, null)

                        if(log_usuatio?.getRol() == 0) modalInvite(view)
                        else modalRegistrado(view)

                        modal.setContentView(view)
                        modal.show()
                    }

            }

        }, email, this)



        recyclerView.layoutManager = LinearLayoutManager(this)



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

        fun modalRegistrado(view: View){

            view.findViewById<TextView>(R.id.txt_modalUserName).text = log_usuatio?.getNombre();
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
            btn_logout.setOnClickListener{
                log_usuatio = Usuario()
                showMessage(this, "Cerrado sesión")
                startActivity(Intent(this, LoginActivity::class.java))
            }

        }
    }