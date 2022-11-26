package com.example.delegadosapp

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.login_register.RegisterActivity
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.example.delegadosapp.vista.publications.PublicationsActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

object AuxFunctions {
    fun showMessage(context:Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showErrorMessage(context:Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}