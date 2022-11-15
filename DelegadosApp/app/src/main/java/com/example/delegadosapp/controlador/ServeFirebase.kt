package com.example.delegadosapp.controlador


import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.util.Log
import com.example.delegadosapp.modelo.Usuario
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object ServeFirebase {

    val db = FirebaseFirestore.getInstance()
    var id: Int = 0

    /* --> Iniciar Sesión <-- */
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    // Choose authentication providers
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build())


    // Create and launch sign-in intent
    val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build()
    signInLauncher.launch(signInIntent)

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }




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

}