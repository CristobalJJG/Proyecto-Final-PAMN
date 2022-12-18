package com.example.delegadosapp.vista.publications

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.delegadosapp.AuxFunctions
import com.example.delegadosapp.CommentsCallback
import com.example.delegadosapp.Publications.mensajesAdapter
import com.example.delegadosapp.R
import com.example.delegadosapp.databinding.ActivitySinglePublicationBinding
import com.example.delegadosapp.modelo.Mensaje
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.listaDelegados.DelegaListActivity
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class SinglePublicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySinglePublicationBinding
    private lateinit var adapter: mensajesAdapter
    private lateinit var title:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()

        binding = ActivitySinglePublicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = intent.getStringExtra("title").toString()
        binding.txtPublicationTitle.text = title
        binding.txtPublicationDesc.text  = intent.getStringExtra("description").toString()
        val storage = Firebase.storage.getReferenceFromUrl("gs://delegaapp.appspot.com/news/" + intent.getStringExtra("picture").toString())
        storage.downloadUrl.addOnSuccessListener { url ->
            Log.i("URL: =>", url.toString())
            Glide.with(this)
                .load(url)
                .into(binding.imgPublication)
        }.addOnFailureListener {
            Log.i("URL: =>", "No se encontró foto")
            binding.imgPublication.visibility = View.GONE
        }

        binding.btnModalMenu.setOnClickListener{
            val modal = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.menu_layout, null)
            modalRegistrado(view)
            modal.setContentView(view)
            modal.show()
        }

        val mensaje = Mensaje()

        mensaje.getComments(
            object: CommentsCallback{
                override fun getComments(comments: Array<String>, context: Context) {
                    val adapter = mensajesAdapter(context)
                    binding.rvMensajes.adapter = adapter
                }
            }, title, this)

        binding.cvComents.visibility = View.GONE



        //Adapter
        adapter = mensajesAdapter(this)

        binding.btnEnviar.setOnClickListener {
            val nombre: String = log_usuario!!.getNombre()
            val img_usuario = log_usuario!!.getImage()
            val mensaje = binding.editTextTextPersonName.text.toString()
            //val hora: String = "00:00"

            val text = Mensaje(nombre = nombre, img = img_usuario, mensaje = mensaje/*, hora = hora*/)
            adapter.addMensaje(text)
            binding.rvMensajes.adapter=adapter
        }
    }

    fun modalRegistrado(view: View){
        view.findViewById<TextView>(R.id.txt_modalUserName).text = log_usuario?.getNombre();
        if(log_usuario?.getRol() == 1) view.findViewById<TextView>(R.id.txt_modalCargo).text = "Alumno"
        else {
            if(log_usuario?.getPuesto() != "") view.findViewById<TextView>(R.id.txt_modalCargo).text =
                log_usuario?.getPuesto()
            else view.findViewById<TextView>(R.id.txt_modalCargo).text = "Delegado, sin más"
        }

        val btn_inicio = view.findViewById<Button>(R.id.btn_menuInicio)
        btn_inicio.visibility = View.VISIBLE
        btn_inicio.setOnClickListener {
            startActivity(Intent(this, PublicationsActivity::class.java))
        }

        val btn_profile = view.findViewById<Button>(R.id.btn_menuProfile)
        btn_profile.visibility = View.VISIBLE
        btn_profile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }

        val btn_favs = view.findViewById<Button>(R.id.btn_menuFavs)
        btn_favs.visibility = View.VISIBLE
        btn_favs.setOnClickListener { AuxFunctions.showMessage(this, "Work In Progress") }

        if (log_usuario?.getRol() == 2) {
            val btn_meetings = view.findViewById<Button>(R.id.btn_menuMeetings)
            btn_meetings.visibility = View.VISIBLE
            btn_meetings.setOnClickListener { AuxFunctions.showMessage(this, "Work In Progress") }
        }

        val btn_listaDelega = view.findViewById<Button>(R.id.btn_menuListDelega)
        btn_listaDelega.visibility = View.VISIBLE
        btn_listaDelega.setOnClickListener {
            startActivity( Intent(this, DelegaListActivity::class.java))
        }

        val btn_logout = view.findViewById<Button>(R.id.btn_menuLogout)
        btn_logout.visibility = View.VISIBLE
        btn_logout.setOnClickListener{
            log_usuario = Usuario()
            Firebase.auth.signOut()
            AuxFunctions.showMessage(this, "Cerrado sesión")
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}