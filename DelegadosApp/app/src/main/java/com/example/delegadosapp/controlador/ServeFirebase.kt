package com.example.delegadosapp.controlador


import android.content.ContentValues.TAG
import android.util.Log
import com.example.delegadosapp.modelo.Usuario
import com.google.firebase.firestore.FirebaseFirestore

object ServeFirebase {

    val db = FirebaseFirestore.getInstance()
    var id: Int = 0

    fun añadirUsuario(usuario: Usuario) {
        id+=1
        val addusuario = hashMapOf(
            "id" to id,
            "rol" to usuario.getRol(),
            "name" to usuario.getNombre(),
            "password" to usuario.getContraseña(),
            "description" to usuario.getDescripcion(),
            "movil" to usuario.getMovil(),
            "email" to usuario.getEmail(),
            "discord" to usuario.getDiscord()
        )
        db.collection("users").document(usuario.getNombre()).set(addusuario).addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun existeUsuario(email: String, contraseña: String): Boolean {
        val usuario = db.collection("user")
        val usuarioConsultaCorreo = usuario.whereEqualTo("email", email)
        val usuarioVerificado = usuario.whereEqualTo("password", contraseña)
        if (usuarioVerificado == null){
            return false
        }
        return true
    }

}