package com.example.delegadosapp.vista.publications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.delegadosapp.AuxFunctions
import com.example.delegadosapp.R
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.listaDelegados.DelegaListActivity
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SinglePublicationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_publication)

        findViewById<CardView>(R.id.cv_coments).visibility = View.GONE

        fun modalRegistrado(view: View){
            view.findViewById<TextView>(R.id.txt_modalUserName).text = log_usuario?.getNombre();
            if(log_usuario?.getRol() == 1) view.findViewById<TextView>(R.id.txt_modalCargo).text = "Alumno"
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

            if(log_usuario?.getRol()==2){
                val btn_meetings = view.findViewById<Button>(R.id.btn_menuMeetings)
                btn_meetings.visibility = View.VISIBLE
                btn_meetings.setOnClickListener{
                    AuxFunctions.showMessage(
                        this,
                        "Work In Progress"
                    )
                }
            }
            val btn_listaDelega = view.findViewById<Button>(R.id.btn_menuListDelega)
            btn_listaDelega.visibility = View.VISIBLE
            btn_listaDelega.setOnClickListener{ startActivity(Intent(this, DelegaListActivity::class.java)) }

            val btn_logout = view.findViewById<Button>(R.id.btn_menuLogout)
            btn_logout.visibility = View.VISIBLE
            btn_logout.setOnClickListener{
                log_usuario = Usuario()
                Firebase.auth.signOut()
                AuxFunctions.showMessage(this, "Cerrado sesión")
                startActivity(Intent(this, LoginActivity::class.java))
            }

        }
    }
}