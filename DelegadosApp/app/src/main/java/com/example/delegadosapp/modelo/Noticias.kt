package com.example.delegadosapp.modelo

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.delegadosapp.NewsCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class Noticias(
    private var title: String = "",
    private var description: String = "",
    private var img: String? = null
) {

    val db = FirebaseFirestore.getInstance()

    fun getTitle(): String { return title }
    fun getDescription(): String { return description }
    fun getImage(): String? { return img }

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
                        document.get("img") as String
                    ))
                }
                myCallback.onCallback(listNews.toTypedArray(),context)
                Log.i("firebase", "Got value ${listNews[0]}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}