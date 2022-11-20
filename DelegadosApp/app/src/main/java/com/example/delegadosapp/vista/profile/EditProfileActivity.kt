package com.example.delegadosapp.vista.profile

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.delegadosapp.R
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.publications.PublicationsActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class EditProfileActivity : AppCompatActivity() {

    lateinit var btn: Button
    //Iniciamos Firebase
    val db = FirebaseFirestore.getInstance()
    lateinit var aux_user: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_edit_profile)

        btn = findViewById(R.id.btn_addInfo)

        btn.setOnClickListener {

            // Extraemos los datos de la pantalla y lo guardamos como variables.
            // para poder guardarlas en el Firebase
            val name = findViewById<EditText>(R.id.editTextTextPersonName3).text.toString()
            val description = findViewById<EditText>(R.id.editTextTextMultiLine2).text.toString()
            val movile = findViewById<EditText>(R.id.editTextTextPersonName5).text.toString()
            val discord = findViewById<EditText>(R.id.editTextTextPersonName7).text.toString()
            val telegram = findViewById<EditText>(R.id.editTextTextPersonName8).text.toString()
            val instagram = findViewById<EditText>(R.id.editTextTextPersonName9).text.toString()


            // Recuperamos los datos del usuario
            val user = Firebase.auth.currentUser
            if (user != null) {
                val addusuario = hashMapOf<String, String>(
                    "name" to name,
                    "description" to description,
                    "movil" to movile,
                    "discord" to discord,
                    "telegram" to telegram,
                    "instagram" to instagram
                )

                user.email?.let { it1 ->
                    db.collection("users").document(it1).set( addusuario ).addOnSuccessListener { Log.d(
                        ContentValues.TAG, "Actualización de los datos") }
                        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
                }
            }

            val intent = Intent(this, PublicationsActivity::class.java)
            startActivity(intent)
        }
    }
}