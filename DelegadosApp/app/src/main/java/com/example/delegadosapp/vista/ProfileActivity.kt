package com.example.delegadosapp.vista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.delegadosapp.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_profile)
    }
}