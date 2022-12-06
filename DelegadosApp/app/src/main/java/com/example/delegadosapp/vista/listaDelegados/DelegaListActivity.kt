package com.example.delegadosapp.vista.listaDelegados

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.AuxFunctions.showMessage
import com.example.delegadosapp.R
import com.google.firebase.firestore.FirebaseFirestore


class DelegaListActivity : AppCompatActivity() {

    private var nombres: ArrayList<String> = ArrayList()
    private var roles: ArrayList<String> = ArrayList()
    private var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delega_list)
        val recyclerView = findViewById<RecyclerView>(R.id.listView)

        nombres.add("Nano")
        nombres.add("Jose")
        roles.add("Suplente derecho")
        roles.add("A pedra")

        val adapter = DelegadosAdapter(nombres, roles)
        recyclerView.adapter = adapter
        Log.i("Fetch_delegados", nombres[0])

        recyclerView.layoutManager = LinearLayoutManager(this)

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
}