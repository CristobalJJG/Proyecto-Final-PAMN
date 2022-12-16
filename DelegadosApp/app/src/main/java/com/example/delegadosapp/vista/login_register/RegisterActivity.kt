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


    //Declaramos FirebaseAuth
    private lateinit var auth: FirebaseAuth
    //Declaramos CloudFirestore
    val db = FirebaseFirestore.getInstance()
    //Nuevo usuario
    private lateinit var newUsuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()

        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinner = binding.spnGrados
        ArrayAdapter.createFromResource(
            this, R.array.grados,
            android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.hiddenPass1.setOnClickListener{
            if(bool1){
                binding.hiddenPass1.setImageResource(R.drawable.esconder)
                binding.txtPass.inputType = 225 //Password_Type
            }else{
                binding.hiddenPass1.setImageResource(R.drawable.ver)
                binding.txtPass.inputType = 64 //Short_Text
            }
            bool1=!bool1
        }
        binding.hiddenPass2.setOnClickListener{
            if(bool2){
                binding.hiddenPass2.setImageResource(R.drawable.esconder)
                binding.txtPass3.inputType = 225 //Password_Type
            }else{
                binding.hiddenPass2.setImageResource(R.drawable.ver)
                binding.txtPass3.inputType = 64 //Short_Text
            }
            bool2=!bool2
        }
    }

    fun onClick_register(view: View){
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        mail = binding.txtMail.text.toString()
        pass = binding.txtPass.text.toString()
        rpt_pass = binding.txtPass3.text.toString()
        val mail_regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$".toRegex()
        val pass_regex = "[a-zA-Z0-9]{6,}".toRegex()
        grado = spinner.selectedItem.toString()
        if(mail.matches(mail_regex) && pass.matches(pass_regex) && pass == rpt_pass) {
            //Iniciamos Firebase.auth
            auth = Firebase.auth
            auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser
                        user?.let {
                            newUsuario =  Usuario(rol = 1,
                                instagram = "@", telegram = "@", nombre = "",
                                descripcion = "", movil = "+34000000000",
                                email = mail, discord = "", grade = "",
                                puesto = ""
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
            else if(pass != rpt_pass) showMessage(baseContext, "Error. Las contraseñas deben coincidir")
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
