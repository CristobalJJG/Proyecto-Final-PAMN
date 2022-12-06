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
import com.example.delegadosapp.Publications.PostAdapter
import com.example.delegadosapp.R
import com.example.delegadosapp.AuxFunctions.showMessage
import com.example.delegadosapp.*
import com.example.delegadosapp.databinding.ActivityPublicationsBinding
import com.example.delegadosapp.modelo.Noticias
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.listaDelegados.DelegaListActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.login_register.RegisterActivity
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore


var log_usuario: Usuario? = null

class PublicationsActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var uid: String
    private var log_usuario: Usuario? = null

    private lateinit var binding: ActivityPublicationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        //setContentView(R.layout.activity_publications)
        binding = ActivityPublicationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Rescatar los datos de las noticias
        val noticias = Noticias()
        val usuario = Usuario()
        
        //Información del usuario que esta logeado
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            this.email = user.email.toString()
            this.uid = user.uid
        }
        Log.w("Correo:        ", email)

        //Llamada a la funcion para rescatar datos
        noticias.datosNoticias(object : NewsCallback {
            override fun onCallback(value: Array<Noticias>) {
                val adapter = PostAdapter(value, {onItemSelected(it)})
                binding.rv.adapter = adapter
            }
        })

        usuario.fetchData(object : UserCallback {
            override fun usuarioCallback(actual_usr: Usuario?, contex: Context) {
                log_usuario=actual_usr
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

                        if(log_usuario?.getRol() == 0) modalInvite(view)
                        else modalRegistrado(view)

                        modal.setContentView(view)
                        modal.show()
                    }
                }
            }, email, this)

            binding.rv.layoutManager = LinearLayoutManager(this)
        }

        fun onItemSelected(noticia: Noticias){
            showMessage(this, noticia.getTitle());
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

        fun modalRegistrado(view: View) {

            view.findViewById<TextView>(R.id.txt_modalUserName).text = log_usuario?.getNombre();
            if (log_usuario?.getRol() == 1) view.findViewById<TextView>(R.id.txt_modalCargo).text = "Alumno"
            else view.findViewById<TextView>(R.id.txt_modalCargo).text = "Delegado"

            val btn_inicio = view.findViewById<Button>(R.id.btn_menuInicio)
            btn_inicio.visibility = View.VISIBLE
            btn_inicio.setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        PublicationsActivity::class.java
                    )
                )
            }

            val btn_profile = view.findViewById<Button>(R.id.btn_menuProfile)
            btn_profile.visibility = View.VISIBLE
            btn_profile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }

            val btn_favs = view.findViewById<Button>(R.id.btn_menuFavs)
            btn_favs.visibility = View.VISIBLE
            btn_favs.setOnClickListener { showMessage(this, "Work In Progress") }

            if (log_usuario?.getRol() == 2) {
                val btn_meetings = view.findViewById<Button>(R.id.btn_menuMeetings)
                btn_meetings.visibility = View.VISIBLE
                btn_meetings.setOnClickListener { showMessage(this, "Work In Progress") }
            }

            val btn_listaDelega = view.findViewById<Button>(R.id.btn_menuListDelega)
            btn_listaDelega.visibility = View.VISIBLE
            btn_listaDelega.setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        DelegaListActivity::class.java
                    )
                )
            }

            val btn_logout = view.findViewById<Button>(R.id.btn_menuLogout)
            btn_logout.visibility = View.VISIBLE
                btn_logout.setOnClickListener {
                    log_usuario = null
                    Firebase.auth.signOut()
                    showMessage(this, "Cerrado sesión")
                    startActivity(Intent(this, LoginActivity::class.java))
                }
        }
    }