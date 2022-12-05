package com.example.delegadosapp.vista.profile

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.delegadosapp.AuxFunctions
import com.example.delegadosapp.MyCallback
import com.example.delegadosapp.R
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.login_register.RegisterActivity
import com.example.delegadosapp.vista.publications.AddNewPublicationActivity
import com.example.delegadosapp.vista.publications.PublicationsActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class ProfileActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var uid: String
    private var log_usuario: Usuario = Usuario()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_profile)

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

        usuario.fetchData(object : MyCallback {

            override fun onCallback(value: Array<String>?, value1: Array<String>?) {
                TODO("Not yet implemented")
            }

            override fun usuarioCallback(actual_usr: Usuario?, contex: Context) {
                if (actual_usr != null) {
                    log_usuario=actual_usr
                    Log.w("USUARIO1", actual_usr.toString())
                }

                findViewById<TextView>(R.id.txt_userName).text = log_usuario.getNombre()
                findViewById<TextView>(R.id.txt_userDesc).text = log_usuario.getDescripcion()
                findViewById<TextView>(R.id.txt_grado).text = log_usuario.getGrade()
                if(log_usuario.getRol() == 2){
                    findViewById<TextView>(R.id.txt_labelDelega).text = "Delegado"
                    findViewById<TextView>(R.id.txt_posDelega).text = "Delegado, sin más"
                }else findViewById<CardView>(R.id.cv_top3).visibility = View.GONE

                if(log_usuario.getMovil() != "")  findViewById<TextView>(R.id.txt_movil).text = "Movil: " + log_usuario.getMovil()
                else findViewById<TextView>(R.id.txt_movil).text = "Movil: X"

                if(log_usuario.getEmail() != "")  findViewById<TextView>(R.id.txt_correo).text = "Correo: " + log_usuario.getEmail()
                else findViewById<TextView>(R.id.txt_correo).text = "Correo: X"

                if(log_usuario.getInstagram() != "null") findViewById<TextView>(R.id.txt_Instagram).text = "Instagram: " + log_usuario.getInstagram()
                else findViewById<TextView>(R.id.txt_Instagram).visibility = View.GONE

                if(log_usuario.getDiscord() != "null")  findViewById<TextView>(R.id.txt_discord).text = "Discord: " + log_usuario.getDiscord()
                else findViewById<TextView>(R.id.txt_discord).visibility = View.GONE

                if(log_usuario.getTelegram() != "null") findViewById<TextView>(R.id.txt_telegram).text = "Telegram: " + log_usuario.getTelegram()
                else findViewById<TextView>(R.id.txt_telegram).visibility = View.GONE
            }
        }, email, this)


        //Log.w("USUARIO", log_usuario.getEmail().toString())



        //Forma de abrir el modal del menú para redirigir a todas las pantallas
        findViewById<FloatingActionButton>(R.id.btn_modalMenu)
            .setOnClickListener {
                val modal = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.menu_layout, null)

                if (log_usuario.getRol() == 0) modalInvite(view)
                else modalRegistrado(view)

                modal.setContentView(view)
                modal.show()
            }
    }

    fun modalInvite(view: View){

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
        view.findViewById<TextView>(R.id.txt_modalUserName).text = "Nombre del usuario"
        if(log_usuario.getRol() == 1) view.findViewById<TextView>(R.id.txt_modalCargo).text = "Alumno"
        else view.findViewById<TextView>(R.id.txt_modalCargo).text = "Delegado"

        val btn_inicio = view.findViewById<Button>(R.id.btn_menuInicio)
        btn_inicio.visibility = View.VISIBLE
        btn_inicio.setOnClickListener{ startActivity(Intent(this, PublicationsActivity::class.java)) }

        val btn_profile = view.findViewById<Button>(R.id.btn_menuProfile)
        btn_profile.visibility = View.VISIBLE
        btn_profile.setOnClickListener{ startActivity(Intent(this, ProfileActivity::class.java)) }

        val btn_favs = view.findViewById<Button>(R.id.btn_menuFavs)
        btn_favs.visibility = View.VISIBLE
        btn_favs.setOnClickListener{ AuxFunctions.showMessage(this, "Work In Progress") }

        if(log_usuario.getRol() == 2){
            val btn_meetings = view.findViewById<Button>(R.id.btn_menuMeetings)
            btn_meetings.visibility = View.VISIBLE
            btn_meetings.setOnClickListener{ AuxFunctions.showMessage(this, "Work In Progress") }
        }
        val btn_listaDelega = view.findViewById<Button>(R.id.btn_menuListDelega)
        btn_listaDelega.visibility = View.VISIBLE
        btn_listaDelega.setOnClickListener{ AuxFunctions.showMessage(this, "Work In Progress") }

        val btn_logout = view.findViewById<Button>(R.id.btn_menuLogout)
        btn_logout.visibility = View.VISIBLE
        btn_logout.setOnClickListener{
            AuxFunctions.showMessage( this, "Asumamos que has cerrado sesión (Spoiler, WIP)" )
        }
    }
}