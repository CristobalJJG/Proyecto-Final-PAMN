package com.example.delegadosapp.vista.login_register

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.delegadosapp.AuxFunctions.showMessage
import com.example.delegadosapp.R
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.profile.EditProfileActivity
import com.example.delegadosapp.vista.publications.PublicationsActivity
import com.example.delegadosapp.vista.publications.log_usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private var mail: String = ""
    private var pass: String = ""
    private var grado: String = ""
    private var rpt_pass: String = ""
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
        rpt_pass = findViewById<TextView>(R.id.txt_pass3).text.toString()
        val mail_regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$".toRegex()
        val pass_regex = "[a-zA-Z0-9]{6,}".toRegex()
        grado = spinner.selectedItem.toString()
        if(mail.matches(mail_regex) && pass.matches(pass_regex) && pass === rpt_pass) {
            //Iniciamos Firebase.auth
            Firebase.auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser
                        user?.let {
                            newUsuario =  Usuario(rol = 1,
                                instagram = "", telegram = "", nombre = "",
                                descripcion = "", movil = "",
                                email = mail, discord = "", grade = spinner.selectedItem.toString(),
                                puesto = "", profile_picture = ""
                            )
                            log_usuario = newUsuario
                            db.collection("users").document(newUsuario.getEmail()).set(newUsuario.getHashUsuario()).addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                        }
                        val intent = Intent(this, EditProfileActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.d(TAG, "RegistrarUsuario: failure", task.exception)
                        showMessage(baseContext, "Error. Hubo algún problema con Firebase (seguramente el correo esté mal o ya esté registrado).")
                    }
                }
            }
        //Control de fallos
        else{
            if(!mail.matches(mail_regex)) showMessage(baseContext, "Error, el correo no está bien formateado (user@host.com).")
            else if(pass.length<6) showMessage(baseContext, "Error, la contraseña tiene que tener mínimo 6 caracteres.")
            else if(!pass.matches(pass_regex)) showMessage(baseContext, "Error. Solo se permiten caracteres alfanuméricos")
            else showMessage(baseContext, "Error. Algo ha fallado, no sabemos el qué, so sorry.")
        }
    }

    fun onClick_goToLogin(view:View){
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("mail", mail)
        startActivity(intent)
    }

    fun onClick_goToInvite(view:View){
        //Se intenta hacer el loggeo
        auth.signInWithEmailAndPassword("invitado@invitado.invitado", "invitado")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, PublicationsActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    showMessage(baseContext, "Authentication failed.")
                }
            }
        showMessage(applicationContext,"Acceso como invitado, habrá ciertas cosas que no podrás hacer")
    }
}

private fun Intent.putExtra(s: String, newUsuario: Usuario) {

}
