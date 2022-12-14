package com.example.delegadosapp.modelo

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.delegadosapp.NewsCallback
import com.example.delegadosapp.Publications.AdapterMensajes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Noticias(
    private var title: String = "",
    private var description: String = "",
    private var img: String? = null,
    private var id: String = ""
) {
    val db = FirebaseFirestore.getInstance()


    fun getTitle(): String { return title }
    fun getDescription(): String { return description }
    fun getImage(): String? { return img }
    fun getId(): String? { return id }

    fun setTitle(string:String) { this.title = string }
    fun setDescription(string:String) { this.description = string }
    fun setImage(string:String) { this.img = string }

    fun datosNoticias(myCallback: NewsCallback, context: Context){
        val listNews: MutableList<Noticias> = mutableListOf()
        db.collection("news")
            .orderBy("fecha", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    listNews.add(Noticias(
                        document.get("title") as String,
                        document.get("description") as String,
                        document.get("img") as String,
                        document.id as String
                    ))
                }
                myCallback.onCallback(listNews.toTypedArray(),context)
                Log.i("firebase", "Got value ${listNews[0]}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
    fun getComentariosCallback(id: String, adapter: AdapterMensajes){
        db.collection("news")
            .document(id)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.get("comentarios") != null) {
                    Log.d("Comentarios", documents.get("comentarios").toString())
                    val mensajes: List<Map<String, Any>> =
                        documents.get("comentarios") as List<Map<String, Any>>
                    for (mensaje in mensajes) {
                        adapter.addMensaje(
                            Mensaje(
                                img = mensaje["imgPerfil"].toString(),
                                hora = mensaje["hora"].toString(),
                                mensaje = mensaje["mensaje"].toString(),
                                nombre = mensaje["nombre"].toString()
                            ), id
                        )
                    }
                }
                //myCallback.getMensajeNews

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting comentarios: ", exception)
            }
    }

}