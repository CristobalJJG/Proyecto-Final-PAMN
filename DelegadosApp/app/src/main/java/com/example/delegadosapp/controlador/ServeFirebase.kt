package com.example.delegadosapp.controlador


import android.content.ContentValues.TAG
import android.util.Log
import com.example.delegadosapp.modelo.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

object ServeFirebase {

    val db = FirebaseFirestore.getInstance()
    var id: Int = 0

    fun añadirUsuario(usuario: Usuario) {
        id+=1
        val addusuario = hashMapOf(
            "id" to id,
            "rol" to usuario.getRol(),
            "password" to usuario.getContraseña(),
            "description" to usuario.getDescripcion(),
            "movil" to usuario.getMovil(),
            "email" to usuario.getEmail(),
            "discord" to usuario.getDiscord()
        )
        db.document(usuario.getNombre()).set(addusuario).addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    /*suspend fun getUsers(): List<User>? {

        return try {
            ListaUsuarios: List<User> = db.collection("users").get()

        }
    }*/

}