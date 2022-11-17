package com.example.delegadosapp.modelo


public class Usuario {

    private var discord: String
    private var email: String
    private var movil: Int
    private var descripcion: String
    private var nombre: String
    private var rol: Int
    private var grade: String
    private var uid: String



    constructor(rol: Int = 1, uid: String = "", nombre: String = "", descripcion: String = "", movil: Int = 0, email: String = "", discord: String = "", grade: String = ""){
        this.rol=rol
        this.uid=uid
        this.nombre=nombre
        this.descripcion=descripcion
        this.movil=movil
        this.email=email
        this.discord=discord
        this.grade=grade
    }

    fun getUid(): String{
        return uid
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

    fun getGrade(): String{
        return grade
    }

    fun getHashUsuario(): HashMap<String, Any?> {
        val addusuario = hashMapOf<String, Any?>(
            "rol" to getRol(),
            "name" to getNombre(),
            "grade" to getGrade(),
            "description" to getDescripcion(),
            "movil" to getMovil(),
            "email" to getEmail(),
            "discord" to getDiscord()
        )
        return addusuario
    }


}