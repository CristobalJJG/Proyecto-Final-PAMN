package com.example.delegadosapp.vista.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.delegadosapp.databinding.ActivityEditProfileBinding
import com.example.delegadosapp.vista.publications.PublicationsActivity
import com.example.delegadosapp.vista.publications.log_usuario
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class EditProfileActivity : AppCompatActivity() {

    lateinit var btn: Button
    //Iniciamos Firebase
    val db = FirebaseFirestore.getInstance()

    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name = log_usuario!!.getNombre()
        var description = log_usuario!!.getDescripcion()
        var movile = log_usuario!!.getMovil()
        var discord = log_usuario!!.getDiscord()
        var telegram = log_usuario!!.getTelegram()
        var instagram = log_usuario!!.getInstagram()
        var profile_picture = log_usuario!!.getProfilePicture()

        binding.btnAddInfo.setOnClickListener {
            // Extraemos los datos de la pantalla y lo guardamos como variables.
            // para poder guardarlas en el Firebase
            var aux = binding.editName.text.toString()
            if(aux != "")name = aux
            aux = binding.editDesc.text.toString()
            if(aux != "") description = aux
            aux = binding.editMovil.text.toString()
            if(aux != "") movile = aux
            aux = binding.editDiscord.text.toString()
            if(aux != "") discord = aux
            aux = binding.editTelegram.text.toString()
            if(aux != "" )telegram = aux
            aux = binding.editInstagram.text.toString()
            if(aux != "") instagram = aux
            /*//Url de la foto, nu sé como va
            aux = binding.editInstagram.text.toString()
            if(aux != "") instagram = aux
            */
            sendInfo(name, description, movile, discord, telegram, instagram)

        }

        binding.btnOmitir.setOnClickListener{
            var aux = log_usuario!!.getNombre()
            if(aux != "")name = aux
            aux = log_usuario!!.getDescripcion()
            if(aux != "") description = aux
            aux = log_usuario!!.getMovil()
            if(aux != "") movile = aux
            aux = log_usuario!!.getDiscord()
            if(aux != "") discord = aux
            aux = log_usuario!!.getTelegram()
            if(aux != "" )telegram = aux
            aux = log_usuario!!.getInstagram()
            if(aux != "") instagram = aux
            /*//Url de la foto, nu sé como va
            aux = log_usuario!!.getProfilePicture()
            if(aux != "") instagram = aux
            */
            sendInfo(name, description, movile, discord, telegram, instagram)
        }
    }

    fun sendInfo(name:String, description:String, movile:String,
                 discord:String, telegram:String, instagram:String){
        // Recuperamos los datos del usuario
        val user = Firebase.auth.currentUser
        if (user != null) {
            val addusuario = hashMapOf<String, String>(
                "name" to name,
                "description" to description,
                "movil" to movile,
                "discord" to discord,
                "telegram" to telegram,
                "instagram" to instagram,
                //"profile_picture" to profile_picture
            )

                user.email?.let {
                    db.collection("users").document(it)
                        .set(addusuario, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d("EditUserInfo => ", "Actualización de los datos")
                        }.addOnFailureListener { e ->
                            Log.e("EditUserInfo => ", "Error writing document", e)
                        }
                }
            }
        }

        val intent = Intent(this, PublicationsActivity::class.java)
        startActivity(intent)
    }
}
