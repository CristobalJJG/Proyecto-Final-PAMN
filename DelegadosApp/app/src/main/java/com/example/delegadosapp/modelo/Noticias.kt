package com.example.delegadosapp.modelo

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.example.delegadosapp.MyCallback
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class Noticias {

    //Firestore
    val db = FirebaseFirestore.getInstance()

    constructor(){

    }

    fun datosNoticias(myCallback: MyCallback){
        val listTitulo: MutableList<String> = mutableListOf()
        val listDescripcion: MutableList<String> = mutableListOf()
        //CoroutineScope(Dispatchers.IO).launch {
            db.collection("news")
                .orderBy("fecha")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        listTitulo.add(document.id)
                        listDescripcion.add(document.get("description") as String)
                    }
                    myCallback.onCallback(listTitulo.toTypedArray(), listDescripcion.toTypedArray())
                    Log.i("firebase", "Got value ${listTitulo[0]}")
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
           // documents.await()
       // }
    }

}