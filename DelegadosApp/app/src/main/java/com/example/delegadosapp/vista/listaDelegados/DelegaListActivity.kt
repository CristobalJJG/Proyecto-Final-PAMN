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
    }
}