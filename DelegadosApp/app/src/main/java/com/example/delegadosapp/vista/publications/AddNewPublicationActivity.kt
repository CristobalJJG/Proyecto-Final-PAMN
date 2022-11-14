package com.example.delegadosapp.vista.publications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.delegadosapp.R

class AddNewPublicationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_add_new_publication)

    }
}