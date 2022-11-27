package com.example.delegadosapp.modelo


class Usuario(
    private var rol: Int = 1,
    private var instagram: String = "@",
    private var telegram: String = "@",
    private var nombre: String = "",
    private var descripcion: String = "",
    private var movil: String = "+34000000000",
    private var email: String = "",
    private var discord: String = "",
    private var grade: String = ""
) {


    fun getDescripcion(): String {
        return descripcion
    }

    fun getRol(): Int {
        return rol
    }

    fun getMovil(): String {
        return movil
    }

    fun getEmail(): String {
        return email
    }

    fun getDiscord(): String {
       return discord
    }

    fun getInstagram():String{
        return instagram
    }

    fun getTelegram():String{
        return telegram
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
            "discord" to getDiscord(),
            "telegram" to getTelegram(),
            "instagram" to getInstagram()
        )
        return addusuario
    }

    fun toUser: Usuario

    override fun toString(): String {
        return "{$nombre, $grade, $rol, $descripcion," +
                "$movil, $email, $telegram, $instagram, $discord}"
    }


}