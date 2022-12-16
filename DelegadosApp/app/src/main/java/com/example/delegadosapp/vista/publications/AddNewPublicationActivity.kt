package com.example.delegadosapp.vista.publications

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.delegadosapp.AuxFunctions.showMessage
import com.example.delegadosapp.R
import com.example.delegadosapp.databinding.ActivityAddNewPublicationBinding
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.listaDelegados.DelegaListActivity
import com.example.delegadosapp.vista.login_register.LoginActivity
import com.example.delegadosapp.vista.login_register.RegisterActivity
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.sql.Timestamp
import java.util.*

class AddNewPublicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewPublicationBinding
    private var new_photo: Boolean = false
    private var data: Uri? = null
    private var newsname: String = ""
    @SuppressLint("InflateParams")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityAddNewPublicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener{
            requestPermissions()
            new_photo=true
        }

        binding.btnModalMenu.setOnClickListener {
                val modal = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.menu_layout, null)

                if (log_usuario?.getRol() == 0) modalInvite(view)
                else modalRegistrado(view)

                modal.setContentView(view)
                modal.show()
        }

        binding.addNews.setOnClickListener {
            title = binding.txtEditTitle.text.toString()
            if(title.length <= 45) {
                if (new_photo) {
                    data?.let { contentResolver.query(it, null, null, null, null) }
                        ?.use {
                            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            it.moveToFirst()
                            newsname = it.getString(nameIndex)
                            Log.w("Nombre archivo ", newsname)
                        }

                    // Create a storage reference from our app
                    val storageRef = Firebase.storage
                        .getReferenceFromUrl("gs://delegaapp.appspot.com/news/" + newsname)
                    // Get the data from an ImageView as bytes
                    val imageView: ImageView = binding.imageView4
                    val bitmap = (imageView.drawable as BitmapDrawable).bitmap
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()

                    val uploadTask = storageRef.putBytes(data)
                    // Handle unsuccessful uploads
                    uploadTask.addOnFailureListener {
                        showMessage(
                            this,
                            "Noooooo, no se ha subido"
                        )
                    }
                        // it.metadata contains file metadata such as size, content-type, etc.
                        .addOnSuccessListener { showMessage(this, "Se ha subido imagen") }
                }
                var calendar = Calendar.getInstance().timeInMillis
                val new_news = hashMapOf(
                    "description" to binding.txtEditDescription.text.toString(),
                    "fecha" to Timestamp(calendar),
                    "title" to title,
                    "img" to newsname
                )

                FirebaseFirestore.getInstance().collection("news").document().set(new_news)
                    .addOnSuccessListener { showMessage(this, "Actualización de los datos") }
                    .addOnFailureListener { showMessage(this, "Error writing document") }
                val intent = Intent(this, PublicationsActivity::class.java)
                startActivity(intent)
            }else{
                showMessage(this, "Los títulos pueden tener como máximo 45 caracteres.")
            }
        }
    }

    private fun requestPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat
                    .checkSelfPermission(this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) -> { escogerFotoGaleria() }
                else -> requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else escogerFotoGaleria()
    }

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
        if (it) escogerFotoGaleria()
        else showMessage(this,"Tienes que dar permisos de acceso a galería")
    }

    private val startForGalleryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.resultCode == Activity.RESULT_OK){
            data = result.data?.data
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
        btn_favs.setOnClickListener{ showMessage(this, "Work In Progress") }

        if(log_usuario?.getRol()==2){
            val btn_meetings = view.findViewById<Button>(R.id.btn_menuMeetings)
            btn_meetings.visibility = View.VISIBLE
            btn_meetings.setOnClickListener{ showMessage(this, "Work In Progress") }
        }
        val btn_listaDelega = view.findViewById<Button>(R.id.btn_menuListDelega)
        btn_listaDelega.visibility = View.VISIBLE
        btn_listaDelega.setOnClickListener{ startActivity(Intent(this, DelegaListActivity::class.java)) }

        val btn_logout = view.findViewById<Button>(R.id.btn_menuLogout)
        btn_logout.visibility = View.VISIBLE
        btn_logout.setOnClickListener{
            log_usuario = Usuario()
            showMessage(this, "Cerrado sesión")
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}