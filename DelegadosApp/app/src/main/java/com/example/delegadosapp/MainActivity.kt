package com.example.delegadosapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Objects


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val db = FirebaseFirestore.getInstance()

        val hashMap:HashMap<String,String> = HashMap<String,String>();
        hashMap.put("firstName","Easy")
        hashMap.put("lastName","Tuto")
        hashMap.put("description","Hola esto es una prueba")

        db.collection("users").add(hashMap)



    }
}