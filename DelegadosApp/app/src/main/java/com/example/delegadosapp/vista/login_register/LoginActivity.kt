package com.example.delegadosapp.vista.login_register

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.delegadosapp.R
import com.example.delegadosapp.AuxFunctions.showMessage
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.publications.PublicationsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


var User:Usuario = Usuario()

class LoginActivity : AppCompatActivity() {
    private var mail:String = ""
    private var pass:String = ""
    private lateinit var btn_login: Button


    //Declaramos FirebaseAuth
    private lateinit var auth: FirebaseAuth
    //Declaramos CloudFirestore
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()

        //Iniciamos Firebase.auth
        auth = Firebase.auth
        setContentView(R.layout.activity_login)

        btn_login = findViewById(R.id.btn_login)
        btn_login.setOnClickListener{
            mail = findViewById<TextView>(R.id.txt_mail).text.toString()
            pass = findViewById<TextView>(R.id.txt_pass).text.toString()
            val mail_regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$".toRegex()
            val pass_regex = "[a-zA-Z0-9]{6,}".toRegex()

            //Se verifica que tengan un buen formato
            if(mail.matches(mail_regex) && pass.matches(pass_regex)) {
                //Se intenta hacer el loggeo
                auth.signInWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, PublicationsActivity::class.java)
                            //fetchData(mail)
                            showMessage(applicationContext, "Has iniciado sesión")
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            showMessage(baseContext, "Authentication failed.")
                        }
                    }
            }
            //Control de fallos
            else{
                if(!mail.matches(mail_regex)) showMessage(baseContext, "Error, el correo no está bien formateado (user@host.com).")
                else if(!pass.matches(pass_regex)) showMessage(baseContext, "Error, contraseña incorrecta. Solo se permiten caracteres alfanuméricos")
                else if(pass.length<6) showMessage(baseContext, "Error, la contraseña tiene que tener mínimo 6 caracteres.")
                else showMessage(baseContext, "Error. Algo ha fallado, no sabemos el qué, so sorry.")
            }
        }
    }

    fun onClick_goToRegister(view:View){
        val intent = Intent(this, RegisterActivity::class.java)
        intent.putExtra("mail", mail)
        startActivity(intent)
        //showMessage(applicationContext, "Irías a 'Register', pero no está hecho todavía, sorry")
    }

    fun onClick_rememberPass(view: View){
        //val intent: Intent = Intent(this,RegisterActivity::class.java)
        //startActivity(intent)
        showMessage(applicationContext, "De alguna forma se te intentaría cambiar la contraseña, pero no está hecho todavía, sorry")
    }

    fun onClick_goToInvite(view:View){
        val intent = Intent(this, PublicationsActivity::class.java)
        startActivity(intent)
        showMessage(applicationContext,"Acceso como invitado, habrá ciertas cosas que no podrás hacer")
    }
}