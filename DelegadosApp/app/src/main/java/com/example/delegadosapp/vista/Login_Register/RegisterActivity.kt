package com.example.delegadosapp.vista.Login_Register

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.delegadosapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.delegadosapp.modelo.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import com.example.delegadosapp.controlador.AuxFunctions.showMessage
import com.example.delegadosapp.vista.Publications.PublicationsActivity

class RegisterActivity : AppCompatActivity() {

    private var mail: String = ""
    private var pass: String = ""
    private var grado: String = ""
    private lateinit var spinner: Spinner

    //Declaramos FirebaseAuth
    private lateinit var auth: FirebaseAuth
    //Declaramos CloudFirestore
    val db = FirebaseFirestore.getInstance()
    //Nuevo usuario
    private lateinit var newUsuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_register)

        //Iniciamos Firebase.auth
        auth = Firebase.auth


        mail = intent.getStringExtra("mail").toString()
        pass = intent.getStringExtra("pass").toString()

        findViewById<TextView>(R.id.txt_mail).text = mail
        findViewById<TextView>(R.id.txt_pass).text = pass

        spinner = findViewById(R.id.spn_grados)
        ArrayAdapter.createFromResource(
            this, R.array.grados,
            android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    fun onClick_register(view: View){
        mail = findViewById<TextView>(R.id.txt_mail).text.toString()
        pass = findViewById<TextView>(R.id.txt_pass).text.toString()
        val regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$".toRegex()
        grado = spinner.selectedItem.toString()
        if(mail.matches(regex)) {
            showMessage(applicationContext,mail + "\n" + pass + "\n" + grado)
            auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        showMessage(baseContext, "Authentication failed.")
                        //updateUI(null)
                    }
                }
            newUsuario = Usuario(email = mail, grade = grado)
            db.collection("users").document(newUsuario.getEmail()).set(newUsuario.getHashUsuario()).addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
            }
        else showMessage(applicationContext,"Error, correo chungo")
    }

    fun onClick_goToLogin(view:View){
        val intent: Intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("mail", mail)
        startActivity(intent)
    }

    fun onClick_goToInvite(view:View){
        val intent: Intent = Intent(this, PublicationsActivity::class.java)
        intent.putExtra("rol", 0)
        startActivity(intent)
        showMessage(applicationContext,"Acceso como invitado, habrá ciertas cosas que no podrás hacer")
    }
}