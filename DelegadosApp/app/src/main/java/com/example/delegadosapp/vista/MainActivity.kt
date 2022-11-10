package com.example.delegadosapp.vista

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.delegadosapp.R
import com.example.delegadosapp.controlador.ServeFirebase
import com.example.delegadosapp.modelo.Usuario


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val jose = Usuario(1,"Jose Manuel Illera",
            "1234", "Soy muy guapo", 693257415,
            "jose.illera234@alu.ulpgc.com", "MaterJoseph#5847")*/

        val result = ServeFirebase.existeUsuario("jose.illera101@alu.ulpgc.es", "1234")

        print(result)
    }
}