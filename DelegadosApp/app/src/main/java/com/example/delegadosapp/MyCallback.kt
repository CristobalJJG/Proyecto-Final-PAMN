package com.example.delegadosapp

import android.content.Context
import com.example.delegadosapp.modelo.Noticias
import com.example.delegadosapp.modelo.Usuario

interface UserCallback {
    fun usuarioCallback(actual_usr: Usuario?, contex: Context)
}

interface NewsCallback {
    fun onCallback(value: Array<Noticias>,contex: Context)
}

interface UsersCallback {
    fun getDelegadosCallback(users: ArrayList<Usuario>)
}