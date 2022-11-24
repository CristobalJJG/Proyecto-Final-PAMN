package com.example.delegadosapp.modelo

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class Noticias {

    //Firestore
    val db = FirebaseFirestore.getInstance()

    constructor(){

    }

    fun tituloNoticias(): Array<String>{
        val listTitulo: MutableList<String> = mutableListOf()
        while (listTitulo.isNotEmpty()) {
            db.collection("news")
                .orderBy("fecha")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        listTitulo.add(document.id)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }
        Log.d( listTitulo[0],"Holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        return listTitulo.toTypedArray()
    }

}