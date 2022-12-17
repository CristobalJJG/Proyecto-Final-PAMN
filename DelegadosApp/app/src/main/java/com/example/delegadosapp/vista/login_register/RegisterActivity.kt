package com.example.delegadosapp.vista.login_register

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.telephony.ims.ImsMmTelManager
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.delegadosapp.AuxFunctions.showMessage
import com.example.delegadosapp.R
import com.example.delegadosapp.databinding.ActivityRegisterBinding
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
    private var rpt_pass: String = ""
    private var grado: String = ""
    private lateinit var spinner: Spinner
    private var bool1 = false
    private var bool2 = false
    private lateinit var binding: ActivityRegisterBinding

    //Declaramos FirebaseAuth
    private lateinit var auth: FirebaseAuth
    //Declaramos CloudFirestore
    val db = FirebaseFirestore.getInstance()
    //Nuevo usuario
    private lateinit var newUsuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Iniciamos Firebase.auth
        auth = Firebase.auth

        spinner = binding.spnGrados
        ArrayAdapter.createFromResource(
            this, R.array.grados,
            android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.hiddenPass1.setOnClickListener{ bool1 = changePassVisibility(binding.hiddenPass1, binding.txtPass, bool1) }
        binding.hiddenPass2.setOnClickListener{ bool2 = changePassVisibility(binding.hiddenPass2, binding.txtPass3, bool2)}
    }

    fun changePassVisibility(iv: ImageView, txt: TextView, bool: Boolean): Boolean {
        if(bool){
            iv.setImageResource(R.drawable.esconder)
            txt.inputType = 225 //Password_Type
        }else{
            iv.setImageResource(R.drawable.ver)
            txt.inputType = 64 //Short_Text
        }
        return !bool
    }

    fun onClick_register(view: View){
        mail = binding.txtMail.text.toString()
        pass = binding.txtPass.text.toString()
        rpt_pass = binding.txtPass3.text.toString()

        val mail_regex = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex()
        val pass_regex = "[a-zA-Z0-9]{6,}".toRegex()

        if(mail.matches(mail_regex) &&
                pass.matches(pass_regex) &&
                pass == rpt_pass) {
            auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser
                        grado = spinner.selectedItem.toString()
                        user?.let {
                            newUsuario = Usuario(
                                rol = 1,
                                instagram = "", telegram = "", nombre = "",
                                descripcion = "", movil = "",
                                email = mail, discord = "", grade = grado,
                                puesto = ""
                            )
                            log_usuario = newUsuario
                            db.collection("users").document(newUsuario.getEmail())
                                .set(newUsuario.getHashUsuario())
                                .addOnSuccessListener { Log.d( TAG, "DocumentSnapshot successfully written!" ) }
                                .addOnFailureListener { e -> Log.w( TAG, "Error writing document", e ) }
                        }
                        startActivity(Intent(this, EditProfileActivity::class.java))
                    } else showMessage(
                        baseContext,
                        "Error. Problema con Firebase (seguramente el correo mal o ya registrado)."
                    )
                }

        //Manejo de errores
        }else {
            Log.d("error", mail.matches(mail_regex).toString() + " " + pass.matches(pass_regex).toString() + " " + (pass != rpt_pass).toString())
            if( !mail.matches(mail_regex) ) showMessage(baseContext, "Error, el correo no está bien formateado (user@host.com).")
            else if( pass.length<6 ) showMessage(baseContext, "Error, la contraseña tiene que tener mínimo 6 caracteres.")
            else if( !pass.matches(pass_regex) ) showMessage(baseContext, "Error. Solo se permiten caracteres alfanuméricos")
            else if( pass != rpt_pass ) showMessage(baseContext, "Error. Las contraseñas deben coincidir")
            else showMessage(baseContext, "Error. Algo ha fallado, no sabemos el qué, so sorry.")
        }
    }

    fun onClick_goToLogin(view:View){
        startActivity(Intent(this, LoginActivity::class.java))
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