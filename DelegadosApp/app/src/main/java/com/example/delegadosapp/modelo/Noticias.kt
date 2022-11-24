package com.example.delegadosapp.modelo

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class Noticias {

    //Firestore
    val db = FirebaseFirestore.getInstance()
    val listTitulo: MutableList<String> = mutableListOf()
    constructor(){

    }

    fun tituloNoticias(): Array<String>{
<<<<<<< Updated upstream
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
=======
        //val listTitulo: MutableList<String> = mutableListOf()
        db.collection("news")
            .orderBy("fecha")
            .get()
            .addOnSuccessListener { documents ->
                //Log.d("hola2", documents.toString())
                for (document in documents) {
                    this.listTitulo.add(document.id)
                    //Log.d("hola1", document.id)
                }
                Log.d("hola", this.listTitulo.toString())
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
        Log.d("hola2", this.listTitulo.toString())
        return this.listTitulo.toTypedArray()
>>>>>>> Stashed changes
    }

}