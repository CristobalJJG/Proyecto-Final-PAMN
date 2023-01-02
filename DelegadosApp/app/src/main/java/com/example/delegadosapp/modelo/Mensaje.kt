package com.example.delegadosapp.modelo

import android.widget.ImageView
import android.widget.TextView

class Mensaje (
    private var mensaje: String,
    private var nombre: String,
    private var img: String,
    private var hora: String
) {


    fun getnombre(): String { return nombre }
    fun getmensaje(): String { return mensaje }
    fun getimgPerfil(): String { return img }
    fun gethora(): String { return hora }

    fun setnombre(string: String){this.nombre= string}
    fun setmensaje(string: String){this.mensaje=string}
    fun setimgPerfil(string: String){this.img=string}
    fun sethora(string: String){this.hora=string}

}