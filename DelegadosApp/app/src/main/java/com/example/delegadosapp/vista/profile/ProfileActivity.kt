package com.example.delegadosapp.vista.profile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.delegadosapp.AuxFunctions
import com.example.delegadosapp.R
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.login_register.RegisterActivity
import com.example.delegadosapp.vista.login_register.User
import com.example.delegadosapp.vista.publications.PublicationsActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_profile)

        findViewById<TextView>(R.id.txt_userName).text = User.getNombre()
        findViewById<TextView>(R.id.txt_userDesc).text = User.getDescripcion()
        findViewById<TextView>(R.id.txt_grado).text = User.getGrade()
        findViewById<TextView>(R.id.txt_labelDelega).text = "Delegado"
        findViewById<TextView>(R.id.txt_posDelega).text = "Delegado, sin más"

        if(User.getMovil() != "")  findViewById<TextView>(R.id.txt_movil).text = "Movil: " + User.getMovil()
        else findViewById<TextView>(R.id.txt_movil).text = "Movil: X"
        if(User.getEmail() != "")  findViewById<TextView>(R.id.txt_correo).text = "Correo: " + User.getEmail()
        else findViewById<TextView>(R.id.txt_correo).text = "Correo: X"
        if(User.getInstagram() != "null") findViewById<TextView>(R.id.txt_Instagram).text = "Instagram: " + User.getInstagram()
        else findViewById<TextView>(R.id.txt_Instagram).text = "Instagram: X"
        if(User.getDiscord() != "null")  findViewById<TextView>(R.id.txt_discord).text = "Discord: " + User.getDiscord()
        else findViewById<TextView>(R.id.txt_discord).text = "Discord: X"
        if(User.getTelegram() != "null") findViewById<TextView>(R.id.txt_telegram).text = "Telegram: " + User.getTelegram()
        else findViewById<TextView>(R.id.txt_telegram).text = "Telegram: X"

        //Forma de abrir el modal del menú para redirigir a todas las pantallas
        findViewById<FloatingActionButton>(R.id.btn_modalMenu)
            .setOnClickListener {
                val modal = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.menu_layout, null)

                if (User.getRol() == 0) modalInvite(view)
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
        btn_favs.setOnClickListener{ AuxFunctions.showMessage(this, "Work In Progress") }

        if(User.getRol() == 2){
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