package com.example.delegadosapp.Publications

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.delegadosapp.R
import com.example.delegadosapp.databinding.MensajesLayoutBinding
import com.example.delegadosapp.modelo.Mensaje
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class mensajesAdapter(private val  context: Context): RecyclerView.Adapter<mensajesAdapter.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
        return ViewHolder(v.inflate(R.layout.mensajes_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) { holder.render(mensajes[i],context) }

    override fun getItemCount(): Int { return mensajes.size }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var binding = MensajesLayoutBinding.bind(itemView)

        fun render (mensaje: Mensaje, context: Context){
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
            /*binding.horaMensaje.text = mensaje.gethora()*/
        }

    }

    val mensajes: MutableList<Mensaje> = mutableListOf()

    fun addMensaje(mensaje: Mensaje){
        mensajes.add(mensaje)
        notifyItemInserted(mensajes.size)
    }
}