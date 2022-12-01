package com.example.delegadosapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.login_register.RegisterActivity
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.example.delegadosapp.vista.publications.PublicationsActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

object AuxFunctions {

    fun showMessage(context:Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showErrorMessage(context:Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}