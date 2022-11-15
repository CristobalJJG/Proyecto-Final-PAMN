package com.example.delegadosapp.vista.Publications

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.R
import com.example.delegadosapp.controlador.AuxFunctions.showMessage
import com.example.delegadosapp.vista.Login_Register.RegisterActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PublicationsActivity : AppCompatActivity() {


    //Declaramos FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_publications)

        //Iniciamos auth
        //auth = intent.get

        /*val rol = intent.getIntExtra("rol", 0)
        showMessage(this, "rol = "+rol)*/
        var user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            this.email = user.email.toString()
            this.uid = user.uid.toString()
        }

        showMessage(this, "email: " + email + "\n" + "uid: " + uid)


        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val adapter = PostAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        //Si el rol es invitado-0 o usuario-1, no se muestra el botón de añadir
        /*if(rol == 0 || rol == 1){
            findViewById<FloatingActionButton>(R.id.btn_addPublication).visibility = View.GONE
        }*/
    }

    fun goToAddPublication(view: View) {
        val intent: Intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        //showMessage(applicationContext,"Irías a 'Register', pero no está hecho todavía, sorry")
    }
}