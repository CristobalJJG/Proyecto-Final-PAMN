package com.example.delegadosapp.modelo

public class Usuario {

    private var discord: String
    private var email: String
    private var movil: Int
    private var descripcion: String
    private var contraseña: String
    private var nombre: String
    private var rol: Int



    constructor(rol: Int, nombre: String, contraseña: String, descripcion: String, movil: Int, email: String, discord: String){
        this.rol=rol
        this.nombre=nombre
        this.contraseña=contraseña
        this.descripcion=descripcion
        this.movil=movil
        this.email=email
        this.discord=discord
    }

    fun getContraseña(): String{
        return contraseña
    }

    fun getDescripcion(): String {
        return descripcion
    }

    fun getRol(): Int {
        return rol
    }

    fun getMovil(): Int {
        return movil
    }

    fun getEmail(): String {
        return email
    }

    fun getDiscord(): String {
       return descripcion
    }

    fun getNombre(): String {
        return nombre
    }


}