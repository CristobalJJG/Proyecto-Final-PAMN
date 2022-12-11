package com.example.delegadosapp.vista.profile

import android.annotation.SuppressLint
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
import com.example.delegadosapp.UserCallback
import com.example.delegadosapp.R
import com.example.delegadosapp.databinding.ActivityProfileBinding
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.listaDelegados.DelegaListActivity
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.publications.PublicationsActivity
import com.example.delegadosapp.vista.publications.log_usuario
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class ProfileActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var uid: String

    private lateinit var binding: ActivityProfileBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Información del usuario que esta logeado
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            this.email = user.email.toString()
            this.uid = user.uid
        }
        binding.txtUserName.text = log_usuario?.getNombre()
        binding.txtUserDesc.text = log_usuario?.getDescripcion()
        binding.txtGrado.text = log_usuario?.getGrade()
        if (log_usuario?.getRol() == 2) {
            binding.txtLabelDelega.text = "Delegado"
            if(log_usuario?.getPuesto() != "") binding.txtPosDelega.text = log_usuario?.getPuesto()
            else binding.txtPosDelega.text = "Delegado, sin más"
        } else binding.cvTop3.visibility = View.GONE

        if (log_usuario?.getMovil() != "") binding.txtMovil.text =
            "Móvil: " + log_usuario?.getMovil()
        else binding.txtMovil.text = "Movil: X"

        if (log_usuario?.getEmail() != "") binding.txtCorreo.text =
            "Correo: " + log_usuario?.getEmail()
        else binding.txtCorreo.text = "Correo: X"

        if (log_usuario?.getInstagram() != "") binding.txtInstagram.text =
            "Instagram: @" + log_usuario?.getInstagram()
        else binding.txtInstagram.visibility = View.GONE

        if (log_usuario?.getDiscord() != "") binding.txtDiscord.text =
            "Discord: " + log_usuario?.getDiscord()
        else binding.txtDiscord.visibility = View.GONE

        if (log_usuario?.getTelegram() != "") binding.txtTelegram.text =
            "Telegram: " + log_usuario?.getTelegram()
        else binding.txtTelegram.visibility = View.GONE

        binding.fabEditProfile.setOnClickListener{
            startActivity( Intent(this, EditProfileActivity::class.java) )
        }

        //Forma de abrir el modal del menú para redirigir a todas las pantallas
        binding.btnModalMenu.setOnClickListener {
                val modal = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.menu_layout, null)
                modalRegistrado(view)
                modal.setContentView(view)
                modal.show()
            }
    }


    fun modalRegistrado(view: View){
        view.findViewById<TextView>(R.id.txt_modalUserName).text = log_usuario?.getNombre();
        if(log_usuario?.getRol() == 1) view.findViewById<TextView>(R.id.txt_modalCargo).text = "Alumno"
        else {
            if(log_usuario?.getPuesto() != "") view.findViewById<TextView>(R.id.txt_modalCargo).text =
                log_usuario?.getPuesto()
            else view.findViewById<TextView>(R.id.txt_modalCargo).text = "Delegado, sin más"
        }

        val btn_inicio = view.findViewById<Button>(R.id.btn_menuInicio)
        btn_inicio.visibility = View.VISIBLE
        btn_inicio.setOnClickListener {
            startActivity(Intent(this, PublicationsActivity::class.java))
        }

        val btn_profile = view.findViewById<Button>(R.id.btn_menuProfile)
        btn_profile.visibility = View.VISIBLE
        btn_profile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }

        val btn_favs = view.findViewById<Button>(R.id.btn_menuFavs)
        btn_favs.visibility = View.VISIBLE
        btn_favs.setOnClickListener { AuxFunctions.showMessage(this, "Work In Progress") }

        if (log_usuario?.getRol() == 2) {
            val btn_meetings = view.findViewById<Button>(R.id.btn_menuMeetings)
            btn_meetings.visibility = View.VISIBLE
            btn_meetings.setOnClickListener { AuxFunctions.showMessage(this, "Work In Progress") }
        }

        val btn_listaDelega = view.findViewById<Button>(R.id.btn_menuListDelega)
        btn_listaDelega.visibility = View.VISIBLE
        btn_listaDelega.setOnClickListener {
            startActivity( Intent(this, DelegaListActivity::class.java))
        }

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