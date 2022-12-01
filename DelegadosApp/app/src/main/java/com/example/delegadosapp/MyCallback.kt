package com.example.delegadosapp

import android.content.Context
import com.example.delegadosapp.modelo.Usuario

interface MyCallback {
    fun onCallback(value: Array<String>?, value1: Array<String>?)
    fun usuarioCallback(actual_usr: Usuario?, contex: Context)
}