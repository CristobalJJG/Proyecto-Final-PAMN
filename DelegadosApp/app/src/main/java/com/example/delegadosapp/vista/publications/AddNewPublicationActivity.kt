package com.example.delegadosapp.vista.publications

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.delegadosapp.AuxFunctions
import com.example.delegadosapp.R
import com.example.delegadosapp.databinding.ActivityAddNewPublicationBinding
import com.example.delegadosapp.databinding.ActivityDelegaListBinding
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.listaDelegados.DelegaListActivity
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.login_register.RegisterActivity
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.jar.Manifest

class AddNewPublicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewPublicationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityAddNewPublicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener{ requestPermissions() }
//        setContentView(R.layout.activity_add_new_publication)
        //Forma de abrir el modal del menú para redirigir a todas las pantallas
        findViewById<FloatingActionButton>(R.id.btn_modalMenu)
            .setOnClickListener {
                val modal = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.menu_layout, null)

                if (log_usuario?.getRol() == 0) modalInvite(view)
                else modalRegistrado(view)

                modal.setContentView(view)
                modal.show()
            }
    }

    private fun requestPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    escogerFotoGaleria()
                }
                else -> requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            escogerFotoGaleria()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted->
        if (isGranted){
            escogerFotoGaleria()
        }else{
            Toast.makeText(this,"Tienes que dar permisos de acceso a galería", Toast.LENGTH_SHORT).show()
        }

    }

    private val startForGalleryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.resultCode == Activity.RESULT_OK){
            val data = result.data?.data
            binding.imageView4.setImageURI(data)
        }

    }

    private fun escogerFotoGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForGalleryActivity.launch(intent)
    }

    fun modalInvite(view:View){
        view.findViewById<TextView>(R.id.txt_modalUserName).text = "Invitado"
        view.findViewById<TextView>(R.id.txt_modalCargo).visibility = View.GONE

        val btn_login = view.findViewById<Button>(R.id.btn_menuLogin)
        btn_login.visibility = View.VISIBLE
        btn_login.setOnClickListener{ startActivity(Intent(this, LoginActivity::class.java)) }

        val btn_register = view.findViewById<Button>(R.id.btn_menuRegister)
        btn_register.visibility = View.VISIBLE
        btn_register.setOnClickListener{ startActivity(Intent(this, RegisterActivity::class.java)) }
    }

    fun modalRegistrado(view: View){
        view.findViewById<TextView>(R.id.txt_modalUserName).text = log_usuario?.getNombre();
        if(log_usuario?.getRol() == 1) view.findViewById<TextView>(R.id.txt_modalCargo).text = "Alumno"
        else view.findViewById<TextView>(R.id.txt_modalCargo).text = "Delegado"

        val btn_inicio = view.findViewById<Button>(R.id.btn_menuInicio)
        btn_inicio.visibility = View.VISIBLE
        btn_inicio.setOnClickListener{ startActivity(Intent(this, PublicationsActivity::class.java)) }

        val btn_profile = view.findViewById<Button>(R.id.btn_menuProfile)
        btn_profile.visibility = View.VISIBLE
        btn_profile.setOnClickListener{ startActivity(Intent(this, ProfileActivity::class.java)) }

        val btn_favs = view.findViewById<Button>(R.id.btn_menuFavs)
        btn_favs.visibility = View.VISIBLE
        btn_favs.setOnClickListener{ AuxFunctions.showMessage(this, "Work In Progress") }

        if(log_usuario?.getRol()==2){
            val btn_meetings = view.findViewById<Button>(R.id.btn_menuMeetings)
            btn_meetings.visibility = View.VISIBLE
            btn_meetings.setOnClickListener{ AuxFunctions.showMessage(this, "Work In Progress") }
        }
        val btn_listaDelega = view.findViewById<Button>(R.id.btn_menuListDelega)
        btn_listaDelega.visibility = View.VISIBLE
        btn_listaDelega.setOnClickListener{ startActivity(Intent(this, DelegaListActivity::class.java)) }

        val btn_logout = view.findViewById<Button>(R.id.btn_menuLogout)
        btn_logout.visibility = View.VISIBLE
        btn_logout.setOnClickListener{
            log_usuario = Usuario()
            AuxFunctions.showMessage(this, "Cerrado sesión")
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}