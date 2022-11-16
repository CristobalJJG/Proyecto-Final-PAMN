package com.example.delegadosapp.vista.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.delegadosapp.R
import com.example.delegadosapp.vista.publications.PublicationsActivity

class EditProfileActivity : AppCompatActivity() {

    lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_edit_profile)

        btn = findViewById(R.id.btn_addInfo)

        btn.setOnClickListener {
            val intent = Intent(this, PublicationsActivity::class.java)
            startActivity(intent)
        }
    }
}