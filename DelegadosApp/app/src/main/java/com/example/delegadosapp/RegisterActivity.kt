package com.example.delegadosapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    private var mail: String = ""
    private var pass: String = ""
    private var grado: String = ""
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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
        if(mail.matches(regex)) showMessage(mail + "\n" + pass + "\n" + grado)
        else showMessage("Error, correo chungo")
    }

    fun onClick_goToLogin(view:View){
        val intent: Intent = Intent(this,LoginActivity::class.java)
        intent.putExtra("mail", mail)
        intent.putExtra("pass", pass)
        startActivity(intent)
    }

    fun onClick_goToInvite(view:View){
        //val intent: Intent = Intent(this,RegisterActivity::class.java)
        //startActivity(intent)
        showMessage("Irías a ver las publciaciones, modo INVITADO, pero no está hecho todavía, sorry")
    }

    fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}