package com.example.delegadosapp.controlador

import android.content.Context
import android.widget.Toast

object AuxFunctions {
    fun showMessage(context:Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showErrorMessage(context:Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}