package com.example.delegadosapp.vista.listaDelegados

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.AuxFunctions
import com.example.delegadosapp.AuxFunctions.showMessage
import com.example.delegadosapp.Publications.PostAdapter
import com.example.delegadosapp.R
import com.example.delegadosapp.UserCallback
import com.example.delegadosapp.UsersCallback
import com.example.delegadosapp.databinding.ActivityDelegaListBinding
import com.example.delegadosapp.databinding.ActivityPublicationsBinding
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.example.delegadosapp.vista.publications.PublicationsActivity
import com.example.delegadosapp.vista.publications.log_usuario
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class DelegaListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_delega_list)

        val binding = ActivityDelegaListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lv.layoutManager = LinearLayoutManager(this)

        val usuario = Usuario()
        usuario.getDelegadosCallback(
            object : UsersCallback {
                override fun getDelegadosCallback(users: ArrayList<Usuario>) {
                    val adapter = DelegadosAdapter(users) { onItemSelected(it) }
                    binding.lv.adapter = adapter
                }
            }
        )
        //Forma de abrir el modal del menú para redirigir a todas las pantallas
        findViewById<FloatingActionButton>(R.id.btn_modalMenu2)
            .setOnClickListener {
                val modal = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.menu_layout, null)

                modalRegistrado(view)

                modal.setContentView(view)
                modal.show()
            }
        }

    fun onItemSelected(user: Usuario){
        Log.i("Delegado Clicked", user.getEmail())
        showMessage(this, user.getEmail())
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