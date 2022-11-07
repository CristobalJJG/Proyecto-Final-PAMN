package com.example.delegadosapp.Publications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.R

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    val titles = arrayOf("Game fest", "Asadero GII/GICD", "Curso de Git")
    val descriptions = arrayOf("Pretende ser un lugar cordial, donde presumir de dotes videojugabilísticas a nivel usuario avanzado.",
        "Pretende ser una concurrecia de personas con intención de socializar, algo que, por lo general, 1 de los 2 programadores de esta aplicación no está acostumbrado, y, por lo tanto, no es fácil explicar como se desarrolla tal actividad",
        "Súper curso impartido por nuestra tan querida profesora MariLola, con el que se pretende obtener los conocimientos básicos de Git para un uso profesional.")
    val images = arrayOf(R.drawable.default_picture, R.drawable.default_picture)

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.post_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.title.text = titles[i]
        holder.description.text = descriptions[i]
        holder.img?.setImageResource(images[i])
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img: ImageView? = null
        var title: TextView
        var description: TextView

        init {
            if(img != null) img = itemView.findViewById(R.id.img_post)
            title = itemView.findViewById(R.id.txt_title)
            description = itemView.findViewById(R.id.txt_description)
        }
    }
}