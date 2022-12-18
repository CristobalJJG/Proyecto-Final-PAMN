package com.example.delegadosapp.modelo

import android.content.Context
import android.util.Log
import com.example.delegadosapp.UserCallback
import com.example.delegadosapp.UsersCallback
import com.google.firebase.firestore.FirebaseFirestore
import java.io.Serializable


class Usuario (
    private var rol: Int = 0,
    private var instagram: String = "@",
    private var telegram: String = "@",
    private var nombre: String = "",
    private var descripcion: String = "",
    private var movil: String = "+34000000000",
    private var email: String = "",
    private var discord: String = "",
    private var grade: String = "",
    private var puesto: String = "",
    private var img: String = ""
): Serializable {
    val db = FirebaseFirestore.getInstance()

    var listOfUsers: ArrayList<Usuario> = ArrayList()

    fun getDescripcion(): String { return descripcion }
    fun getRol(): Int { return rol }
    fun getMovil(): String { return movil  }
    fun getEmail(): String { return email }
    fun getDiscord(): String { return discord }
    fun getInstagram():String{ return instagram }
    fun getTelegram():String{ return telegram }
    fun getNombre(): String { return nombre }
    fun getGrade(): String{ return grade }
    fun getPuesto(): String{ return puesto }
    fun getImage(): String { return img }

    fun setDescripcion(string: String) { this.descripcion = string }
    fun setRol(string: Int) { this.rol = string }
    fun setMovil(string: String) { this.movil = string }
    fun setEmail(string: String) { this.email = string }
    fun setDiscord(string: String) { this.discord = string }
    fun setInstagram(string: String){ this.instagram = string }
    fun setTelegram(string: String){ this.telegram = string }
    fun setNombre(string: String) { this.nombre = string }
    fun setGrade(string: String){ this.grade = string }
    fun setPuesto(string: String){ this.puesto = string }
    fun setImage(string: String){ this.img = string }

    fun getHashUsuario(): HashMap<String, Any?> {
        val addusuario = hashMapOf<String, Any?>(
            "rol" to getRol(),
            "puesto" to getPuesto(),
            "name" to getNombre(),
            "grade" to getGrade(),
            "description" to getDescripcion(),
            "movil" to getMovil(),
            "email" to getEmail(),
            "discord" to getDiscord(),
            "telegram" to getTelegram(),
            "instagram" to getInstagram(),
            "img" to getImage()
        )
        return addusuario
    }

    override fun toString(): String {
        return "$nombre, $grade, $rol, $descripcion," +
                "$movil, $email, $telegram, $instagram, " +
                "$discord, $img"
    }

    fun fetchData(myCallback: UserCallback, email: String, contex: Context){
        db.collection("users")
            .document(email)
            .get()
            .addOnSuccessListener { doc ->
                setRol(doc.data?.get("rol").toString().toInt())
                setInstagram(doc.data?.get("instagram").toString())
                setTelegram(doc.data?.get("telegram").toString())
                setNombre(doc.data?.get("name").toString())
                setDescripcion(doc.data?.get("description").toString())
                setMovil(doc.data?.get("movil").toString())
                setEmail(doc.data?.get("email").toString())
                setDiscord(doc.data?.get("discord").toString())
                setPuesto(doc.data?.get("puesto").toString())
                setGrade(doc.data?.get("grade").toString())
                setImage(doc.data?.get("img").toString())
                Log.w("USUARIO1", this.toString())
                myCallback.usuarioCallback(this, contex)
            }
    }

    fun getDelegadosCallback(myCallback: UsersCallback, context: Context) {
        db.collection("users")
            .whereEqualTo("rol",  2)
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val rol = (doc.data["rol"].toString().toInt())
                        val insta = (doc.data["instagram"].toString())
                        val telegram = (doc.data["telegram"].toString())
                        val nombre = (doc.data["name"].toString())
                        val desc = (doc.data["description"].toString())
                        val movil = (doc.data["movil"].toString())
                        val email = (doc.data["email"].toString())
                        val discord = (doc.data["discord"].toString())
                        val grade = (doc.data["grade"].toString())
                        val puesto = (doc.data["puesto"].toString())
                        val img = (doc.data["img"].toString())
                        val profile_picture = (doc.data["profile_picture"]).toString()
                        listOfUsers.add(
                            Usuario(
                                rol, insta, telegram,
                                nombre, desc, movil,
                                email, discord, grade, puesto,
                                img
                            )
                        )
                }
                myCallback.getDelegadosCallback(listOfUsers, context)
            }
    }
}
