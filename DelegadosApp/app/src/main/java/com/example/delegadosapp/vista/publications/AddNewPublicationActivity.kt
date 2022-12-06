package com.example.delegadosapp.vista.publications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.delegadosapp.AuxFunctions
import com.example.delegadosapp.R
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.listaDelegados.DelegaListActivity
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.login_register.RegisterActivity
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewPublicationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_add_new_publication)
        //Forma de abrir el modal del menú para redirigir a todas las pantallas
        findViewById<FloatingActionButton>(R.id.btn_modalMenu)
            .setOnClickListener {
                val modal = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.menu_layout, null)

                if (log_usuario?.getRol() == 0) modalInvite(view)
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
            btn_meetings.setOnClickListener{ AuxFunctions.showMessage(this, "Work In Progress") }
        }
        val btn_listaDelega = view.findViewById<Button>(R.id.btn_menuListDelega)
        btn_listaDelega.visibility = View.VISIBLE
        btn_listaDelega.setOnClickListener{ startActivity(Intent(this, DelegaListActivity::class.java)) }

        val btn_logout = view.findViewById<Button>(R.id.btn_menuLogout)
        btn_logout.visibility = View.VISIBLE
        btn_logout.setOnClickListener{
            log_usuario = Usuario()
            AuxFunctions.showMessage(this, "Cerrado sesión")
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}