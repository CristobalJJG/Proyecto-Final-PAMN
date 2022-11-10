package com.example.delegadosapp.vista.Login_Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.delegadosapp.vista.Publications.PublicationsActivity
import com.example.delegadosapp.R

class LoginActivity : AppCompatActivity() {
    private var mail:String = ""
    private var pass:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    fun onClick_login(view: View) {
        mail = findViewById<TextView>(R.id.txt_mail).text.toString()
        pass = findViewById<TextView>(R.id.txt_pass).text.toString()
        val regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$".toRegex()

        if(mail.matches(regex)) showMessage(mail + "\n" + pass)
        else showMessage("Error, correo chungo")
    }

    fun onClick_goToRegister(view:View){
        val intent: Intent = Intent(this, RegisterActivity::class.java)
        intent.putExtra("mail", mail)
        intent.putExtra("pass", pass)
        startActivity(intent)
        //showMessage("Irías a 'Register', pero no está hecho todavía, sorry")
    }

    fun onClick_rememberPass(view: View){
        //val intent: Intent = Intent(this,RegisterActivity::class.java)
        //startActivity(intent)
        showMessage("De alguna forma se te intentaría cambiar la contraseña, pero no está hecho todavía, sorry")
    }

    fun onClick_goToInvite(view:View){
        val intent: Intent = Intent(this, PublicationsActivity::class.java)
        startActivity(intent)
        showMessage("Acceso como invitado, habrá ciertas cosas que no podrás hacer")
        //showMessage("Irías a ver las publciaciones, modo INVITADO, pero no está hecho todavía, sorry")
    }

    fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}