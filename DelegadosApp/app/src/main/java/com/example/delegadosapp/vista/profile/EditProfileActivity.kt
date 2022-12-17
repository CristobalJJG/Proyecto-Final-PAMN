package com.example.delegadosapp.vista.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.delegadosapp.AuxFunctions
import com.example.delegadosapp.databinding.ActivityEditProfileBinding
import com.example.delegadosapp.vista.publications.PublicationsActivity
import com.example.delegadosapp.vista.publications.log_usuario
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class EditProfileActivity : AppCompatActivity() {

    lateinit var btn: Button
    //Iniciamos Firebase
    val db = FirebaseFirestore.getInstance()
    private var new_photo: Boolean = false
    private var data: Uri? = null
    private var newsname: String = ""

    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var email = log_usuario!!.getEmail()
        var name = log_usuario!!.getNombre()
        var description = log_usuario!!.getDescripcion()
        var movile = log_usuario!!.getMovil()
        var discord = log_usuario!!.getDiscord()
        var telegram = log_usuario!!.getTelegram()
        var instagram = log_usuario!!.getInstagram()
        var profile_picture = log_usuario!!.getProfilePicture()

        binding.btnAddInfo.setOnClickListener {
            // Extraemos los datos de la pantalla y lo guardamos como variables.
            // para poder guardarlas en el Firebase
            var aux = binding.editName.text.toString()
            if(aux != "")name = aux
            aux = binding.editDesc.text.toString()
            if(aux != "") description = aux
            aux = binding.editMovil.text.toString()
            if(aux != "") movile = aux
            aux = binding.editDiscord.text.toString()
            if(aux != "") discord = aux
            aux = binding.editTelegram.text.toString()
            if(aux != "" )telegram = aux
            aux = binding.editInstagram.text.toString()
            if(aux != "") instagram = aux
            /*//Url de la foto, nu sé como va
            aux = binding.editInstagram.text.toString()
            if(aux != "") instagram = aux
            */
            sendInfo(name, description, movile, discord, telegram, instagram)

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
                    .getReferenceFromUrl("gs://delegaapp.appspot.com/users/" + newsname)
                // Get the data from an ImageView as bytes
                val imageView: ImageView = binding.imageView6
                val bitmap = (imageView.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                val uploadTask = storageRef.putBytes(data)
                // Handle unsuccessful uploads
                uploadTask.addOnFailureListener {
                    AuxFunctions.showMessage(
                        this,
                        "Noooooo, no se ha subido"
                    )
                }
                    // it.metadata contains file metadata such as size, content-type, etc.
                    .addOnSuccessListener { AuxFunctions.showMessage(this, "Se ha subido imagen") }

                val new_news = hashMapOf(
                    "img" to newsname
                )

                FirebaseFirestore.getInstance().collection("users").document(email).set(new_news, SetOptions.merge())
                    .addOnSuccessListener {
                        AuxFunctions.showMessage(
                            this,
                            "Actualización de los datos"
                        )
                    }
                    .addOnFailureListener {
                        AuxFunctions.showMessage(
                            this,
                            "Error writing document"
                        )
                    }
            }
        }

        binding.btnOmitir.setOnClickListener{
            var aux = log_usuario!!.getNombre()
            if(aux != "")name = aux
            aux = log_usuario!!.getDescripcion()
            if(aux != "") description = aux
            aux = log_usuario!!.getMovil()
            if(aux != "") movile = aux
            aux = log_usuario!!.getDiscord()
            if(aux != "") discord = aux
            aux = log_usuario!!.getTelegram()
            if(aux != "" )telegram = aux
            aux = log_usuario!!.getInstagram()
            if(aux != "") instagram = aux
            /*//Url de la foto, nu sé como va
            aux = log_usuario!!.getProfilePicture()
            if(aux != "") instagram = aux
            */
            sendInfo(name, description, movile, discord, telegram, instagram)
        }

        binding.button2.setOnClickListener {
            requestPermissions()
            new_photo=true
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
        else AuxFunctions.showMessage(this, "Tienes que dar permisos de acceso a galería")
    }

    private val startForGalleryActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.resultCode == Activity.RESULT_OK){
            data = result.data?.data
            binding.imageView6.setImageURI(data)
        }
    }

    private fun escogerFotoGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForGalleryActivity.launch(intent)
    }


    fun sendInfo(name:String, description:String, movile:String,
                 discord:String, telegram:String, instagram:String){
        // Recuperamos los datos del usuario
        val user = Firebase.auth.currentUser
        if (user != null) {
            val addusuario = hashMapOf<String, String>(
                "name" to name,
                "description" to description,
                "movil" to movile,
                "discord" to discord,
                "telegram" to telegram,
                "instagram" to instagram,
                //"profile_picture" to profile_picture
            )

            user.email?.let {
                db.collection("users").document(it)
                    .set(addusuario, SetOptions.merge())
                    .addOnSuccessListener { Log.d("EditUserInfo => ", "Actualización de los datos") }
                    .addOnFailureListener { e -> Log.e("EditUserInfo => ", "Error writing document", e) }
            }
        }
        startActivity(Intent(this, PublicationsActivity::class.java))
    }
}
