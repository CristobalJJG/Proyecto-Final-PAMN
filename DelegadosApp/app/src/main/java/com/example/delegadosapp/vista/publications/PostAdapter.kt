package com.example.delegadosapp.Publications

import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.AuxFunctions.showMessage
import com.example.delegadosapp.R
import com.example.delegadosapp.databinding.PostLayoutBinding
import com.example.delegadosapp.modelo.Noticias
import com.example.delegadosapp.vista.publications.SinglePublicationActivity

class PostAdapter(
    private val news: Array<Noticias>,
    private val onClickListener: (Noticias) -> Unit) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.post_layout, parent, false))
        //return PostLayoutBinding.bind(parent:View)
    }

    override fun onBindViewHolder(vh: ViewHolder, i: Int) {
        vh.render(news[i], onClickListener)
    }

    override fun getItemCount(): Int = news.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = PostLayoutBinding.bind(itemView)

        fun render(news: Noticias, onClickListener: (Noticias) -> Unit){
            //img?.let { binding.imgPost.setImageResource(news.getImage()) }
            binding.txtTitle.text = news.getTitle()
            binding.txtDescription.text = news.getDescription()

            binding.cvHolder.setOnClickListener{ onClickListener(news) }
        }
    }
}