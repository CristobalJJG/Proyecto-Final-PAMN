package com.example.delegadosapp.vista.Login_Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.delegadosapp.vista.Publications.PublicationsActivity
import com.example.delegadosapp.R
import com.example.delegadosapp.controlador.AuxFunctions.showMessage

class LoginActivity : AppCompatActivity() {
    private var mail:String = ""
    private var pass:String = ""
    private lateinit var btn_login: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        btn_login = findViewById(R.id.btn_login)
        btn_login.setOnClickListener{

            mail = findViewById<TextView>(R.id.txt_mail).text.toString()
            pass = findViewById<TextView>(R.id.txt_pass).text.toString()
            val regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$".toRegex()

            if(mail.matches(regex)) showMessage(applicationContext, mail + "\n" + pass)
            else showMessage( applicationContext,"Error, correo chungo")
        }
    }

    fun onClick_goToRegister(view:View){
        val intent: Intent = Intent(this, RegisterActivity::class.java)
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
        val intent: Intent = Intent(this, PublicationsActivity::class.java)
        intent.putExtra("rol", 0)
        startActivity(intent)
        showMessage(applicationContext,"Acceso como invitado, habrá ciertas cosas que no podrás hacer")
    }
}