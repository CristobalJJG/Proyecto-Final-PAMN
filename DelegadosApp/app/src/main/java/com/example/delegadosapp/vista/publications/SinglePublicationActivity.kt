package com.example.delegadosapp.vista.publications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import com.example.delegadosapp.R

class SinglePublicationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_publication)

        findViewById<CardView>(R.id.cv_coments).visibility = View.GONE
    }
}