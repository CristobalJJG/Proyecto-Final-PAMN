package com.example.delegadosapp.vista.listaDelegados

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.AuxFunctions.showMessage
import com.example.delegadosapp.Publications.PostAdapter
import com.example.delegadosapp.R
import com.example.delegadosapp.UserCallback
import com.example.delegadosapp.UsersCallback
import com.example.delegadosapp.databinding.ActivityDelegaListBinding
import com.example.delegadosapp.databinding.ActivityPublicationsBinding
import com.example.delegadosapp.modelo.Usuario
import com.google.firebase.firestore.FirebaseFirestore


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
                    val adapter = DelegadosAdapter(users, {onItemSelected(it)})
                    binding.lv.adapter = adapter
                }
            }
        )
    }

    fun onItemSelected(user: Usuario){
        showMessage(this, user.getEmail());
    }

        /*
        fun modalRegistrado(view: View) {

            view.findViewById<TextView>(R.id.txt_modalUserName).text = log_usuatio?.getNombre();
            if (User.getRol() == 1) view.findViewById<TextView>(R.id.txt_modalCargo).text = "Alumno"
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

            if (User.getRol() == 2) {
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
                log_usuatio = Usuario()
                showMessage(this, "Cerrado sesión")
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        */

}