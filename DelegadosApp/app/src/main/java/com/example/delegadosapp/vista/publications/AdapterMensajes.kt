package com.example.delegadosapp.Publications

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.delegadosapp.R
import com.example.delegadosapp.databinding.CardViewMensajesBinding
import com.example.delegadosapp.modelo.Mensaje
import com.example.delegadosapp.modelo.Noticias
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AdapterMensajes(
    private val  context: Context
): RecyclerView.Adapter<AdapterMensajes.ViewHolder> (){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var binding = CardViewMensajesBinding.bind(itemView)
        /*
        var nombre =  binding.txtUsername
        var mensaje = binding.txtMensaje
        var imgPerfil = binding.imgUserProfile
        var hora = binding.horaMensaje

        fun getnombre(): TextView { return nombre }
        fun getmensaje(): TextView { return mensaje }
        fun getimgPerfil(): ImageView { return imgPerfil }
        fun gethora(): TextView { return hora }

        fun setnombre(nombre: TextView){this.nombre=nombre}
        fun setmensaje(mensaje: TextView){this.mensaje=mensaje}
        fun setimgPerfil(imgPerfil: ImageView){this.imgPerfil=imgPerfil}
        fun sethora(hora: TextView){this.hora=hora}*/

        fun render (mensaje: Mensaje, context: Context){

            // Get a non-default Storage bucket
            val storage = Firebase.storage.getReferenceFromUrl("gs://delegaapp.appspot.com/users/" + mensaje.getimgPerfil())
            storage.downloadUrl.addOnSuccessListener { url ->
                Log.i("URL: =>", url.toString())
                Glide.with(context)
                    .load(url)
                    .into(binding.imgUserProfile)
            }.addOnFailureListener {
                Log.i("URL: =>", "No se encontró foto")
                binding.imgUserProfile.visibility = View.GONE
            }


            binding.txtUsername.text = mensaje.getnombre()
            binding.txtMensaje.text = mensaje.getmensaje()
            binding.horaMensaje.text = mensaje.gethora()
        }

    }

    val mensajes: MutableList<Mensaje> = mutableListOf()

    fun addMensaje(mensaje: Mensaje){
        mensajes.add(mensaje)
        notifyItemInserted(mensajes.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.card_view_mensajes,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(mensajes[position],context)
    }


    override fun getItemCount(): Int {
        return mensajes.size
    }


}