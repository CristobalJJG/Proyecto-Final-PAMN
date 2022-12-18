package com.example.delegadosapp.modelo

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.delegadosapp.CommentsCallback
import com.example.delegadosapp.vista.publications.SinglePublicationActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log

class Mensaje (
    private var mensaje: String = "",
    private var nombre: String = "",
    private var img: String = "",
    /*private var hora: String*/
) {
    val db = FirebaseFirestore.getInstance()

    fun getnombre(): String { return nombre }
    fun getmensaje(): String { return mensaje }
    fun getimgPerfil(): String { return img }
    /*fun gethora(): String { return hora }*/

    fun setnombre(string: String){ this.nombre = string}
    fun setmensaje(string: String){ this.mensaje = string }
    fun setimgPerfil(string: String){ this.img = string }
    /*fun sethora(string: String){this.hora=string}*/

    fun getComments(myCallback: CommentsCallback, noticia: String, context: Context){
        db.collection("news")
            .whereEqualTo("title", noticia)
            .get()
            .addOnSuccessListener { documents ->
                var commentsList: ArrayList<Mensaje> = ArrayList()
                for (document in documents) {
                    Log.d("devuelve", document.get("comments").toString())
                    //MensajecommentsList = (document.get("comments") as ArrayList<*>)
                }
                myCallback.getComments(commentsList,context)
                //Log.d("firebase", "Got value ${commentsList[0]}")
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }
}