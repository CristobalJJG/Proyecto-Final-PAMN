package com.example.delegadosapp.vista.login_register

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.delegadosapp.AuxFunctions.showMessage
import com.example.delegadosapp.R
import com.example.delegadosapp.databinding.ActivityRecoveryPasswordBinding
import com.example.delegadosapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RecoveryPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecoveryPasswordBinding
    private val mail_regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$".toRegex()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoveryPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.btnRecovery.setOnClickListener{
            val recovery_mail = binding.txtRecoveryMail.text.toString()
            if(recovery_mail.matches(mail_regex)){
                resetPassword(recovery_mail)
            }else showMessage(this, "Error, el correo que has introducido no está bien formateado (user@host.com)")
        }
    }

    private fun resetPassword(email: String) {
        auth.setLanguageCode("es")
        auth.sendPasswordResetEmail(email).addOnCompleteListener { mail ->
            if(mail.isSuccessful ){
                showMessage(
                    this,
                    "Se ha enviado un correo para restablecer contraseña"
                )
            }else{
                showMessage(
                    this,
                    "No se pudo restablecer contraseña"
                )
            }
        }

    }
}