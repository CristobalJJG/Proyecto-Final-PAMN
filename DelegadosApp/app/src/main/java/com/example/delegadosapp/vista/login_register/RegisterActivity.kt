package com.example.delegadosapp.vista.login_register

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
import com.example.delegadosapp.AuxFunctions.showMessage
import com.example.delegadosapp.vista.profile.EditProfileActivity
import com.example.delegadosapp.vista.publications.PublicationsActivity

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
        val mail_regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$".toRegex()
        val pass_regex = "[a-zA-Z0-9]{6,}".toRegex()
        grado = spinner.selectedItem.toString()
        if(mail.matches(mail_regex) && pass.matches(pass_regex)) {
            auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser
                        user?.let {
                            // Name, email address, and profile photo Url
                            val email = user.email
                            val uid = user.uid
                            newUsuario = Usuario(uid = uid, email = mail, grade = grado)
                            db.collection("users").document(newUsuario.getEmail()).set(newUsuario.getHashUsuario()).addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                        }
                        val intent = Intent(this, EditProfileActivity::class.java)
                        intent.putExtra("newUser", newUsuario)
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
        val intent = Intent(this, PublicationsActivity::class.java)
        intent.putExtra("rol", 0)
        startActivity(intent)
        showMessage(applicationContext,"Acceso como invitado, habrá ciertas cosas que no podrás hacer")
    }
}

private fun Intent.putExtra(s: String, newUsuario: Usuario) {

}
