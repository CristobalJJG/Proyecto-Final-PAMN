package com.example.delegadosapp.Publications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.R

class PostAdapter(
    private val titles: Array<String>, private val descriptions: Array<String>,
    private val images: Array<Int?>, private val rol: Int) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.post_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(vh: ViewHolder, i: Int) {
        vh.title.text = titles[i]
        vh.description.text = descriptions[i]
        if(images[i] != null) images[i]?.let { vh.img.setImageResource(it) }


    }

    override fun getItemCount(): Int { return titles.size }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var title: TextView
        var description: TextView
        var imgPart: CardView

        init {
            imgPart = itemView.findViewById(R.id.cv_photo)
            img = itemView.findViewById(R.id.img_post)
            title = itemView.findViewById(R.id.txt_title)
            description = itemView.findViewById(R.id.txt_description)
        }
    }
}