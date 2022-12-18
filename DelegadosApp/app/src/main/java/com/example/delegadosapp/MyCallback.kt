package com.example.delegadosapp

import android.content.Context
import com.example.delegadosapp.modelo.Mensaje
import com.example.delegadosapp.modelo.Noticias
import com.example.delegadosapp.modelo.Usuario

interface UserCallback {
    fun usuarioCallback(actual_usr: Usuario?, context: Context)
}

interface NewsCallback {
    fun onCallback(value: Array<Noticias>,context: Context)
}

interface UsersCallback {
    fun getDelegadosCallback(users: ArrayList<Usuario>,context: Context)
}

interface CommentsCallback {
    fun getComments(comments: ArrayList<Mensaje>, context: Context)
}