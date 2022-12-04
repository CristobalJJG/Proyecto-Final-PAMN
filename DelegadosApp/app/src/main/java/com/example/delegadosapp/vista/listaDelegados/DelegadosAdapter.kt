package com.example.delegadosapp.vista.listaDelegados

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.R

class DelegadosAdapter (
    private val nombres: ArrayList<String>, private val roles: ArrayList<String>,
    /*private val images: Array<Int?>*/) : RecyclerView.Adapter<DelegadosAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.delegado_layout, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(vh: ViewHolder, i: Int) {
            vh.nombre.text = nombres[i]
            vh.rol.text = roles[i]
            //if(images[i] != null) images[i]?.let { vh.img.setImageResource(it) }
        }

        override fun getItemCount(): Int { return nombres.size }

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var img: ImageView
            var nombre: TextView
            var rol: TextView
            //var imgPart: CardView

            init {
                //imgPart = itemView.findViewById(R.id.cv_photo)
                img = itemView.findViewById(R.id.img_userProfile)
                nombre = itemView.findViewById(R.id.txt_Username)
                rol = itemView.findViewById(R.id.txt_delegaRol)
            }
        }
}