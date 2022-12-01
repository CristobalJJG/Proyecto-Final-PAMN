package com.example.delegadosapp.modelo

import android.content.Context
import com.example.delegadosapp.MyCallback
import com.google.firebase.firestore.FirebaseFirestore


class Usuario(
    private var rol: Int = 0,
    private var instagram: String = "@",
    private var telegram: String = "@",
    private var nombre: String = "",
    private var descripcion: String = "",
    private var movil: String = "+34000000000",
    private var email: String = "",
    private var discord: String = "",
    private var grade: String = ""
) {

    fun getDescripcion(): String { return descripcion }
    fun getRol(): Int { return rol }
    fun getMovil(): String { return movil  }
    fun getEmail(): String { return email }
    fun getDiscord(): String { return discord }
    fun getInstagram():String{ return instagram }
    fun getTelegram():String{ return telegram }
    fun getNombre(): String { return nombre }
    fun getGrade(): String{ return grade }

    fun setDescripcion(string: String) { this.descripcion = string }
    fun setRol(string: Int) { this.rol = string }
    fun setMovil(string: String) { this.movil = string }
    fun setEmail(string: String) { this.email = string }
    fun setDiscord(string: String) { this.discord = string }
    fun setInstagram(string: String){ this.instagram = string }
    fun setTelegram(string: String){ this.telegram = string }
    fun setNombre(string: String) { this.nombre = string }
    fun setGrade(string: String){ this.grade = string }

    fun getHashUsuario(): HashMap<String, Any?> {
        val addusuario = hashMapOf<String, Any?>(
            "rol" to getRol(),
            "name" to getNombre(),
            "grade" to getGrade(),
            "description" to getDescripcion(),
            "movil" to getMovil(),
            "email" to getEmail(),
            "discord" to getDiscord(),
            "telegram" to getTelegram(),
            "instagram" to getInstagram()
        )
        return addusuario
    }

    override fun toString(): String {
        return "$nombre, $grade, $rol, $descripcion," +
                "$movil, $email, $telegram, $instagram, $discord"
    }

    fun fetchData(myCallback: MyCallback, email: String, contex: Context){
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(email)
            .get()
            .addOnSuccessListener { doc ->
                setRol(doc.data?.get("rol").toString().toInt())
                setInstagram(doc.data?.get("instagram").toString())
                setTelegram(doc.data?.get("telegram").toString())
                setNombre(doc.data?.get("name").toString())
                setDescripcion(doc.data?.get("description").toString())
                setMovil(doc.data?.get("movil").toString())
                setEmail(doc.data?.get("email").toString())
                setDiscord(doc.data?.get("discord").toString())
                setGrade(doc.data?.get("grade").toString())
                myCallback.usuarioCallback(this, contex)
            }
    }

}