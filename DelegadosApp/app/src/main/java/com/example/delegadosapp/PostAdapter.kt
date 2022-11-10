package com.example.delegadosapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    val titles = arrayOf("Nano", "Jose", "Cristóbal")
    val descriptions = arrayOf("Nano", "Jose", "Cristóbal")
    val images = arrayOf(R.drawable.default_picture, R.drawable.default_picture, R.drawable.default_picture)

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.post_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.title.text = titles[i]
        holder.description.text = descriptions[i]
        holder.img.setImageResource(images[i])
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var title: TextView
        var description: TextView

        init {
            img = itemView.findViewById(R.id.img_post)
            title = itemView.findViewById(R.id.txt_title)
            description = itemView.findViewById(R.id.txt_description)
        }
    }
}