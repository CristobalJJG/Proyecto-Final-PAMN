package com.example.delegadosapp.Publications

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.delegadosapp.R
import com.example.delegadosapp.databinding.PostLayoutBinding
import com.example.delegadosapp.modelo.Noticias
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class PostAdapter(
    private val news: Array<Noticias>,
    private val onClickListener: (Noticias) -> Unit,
    private val  context: Context) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

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
        var binding = PostLayoutBinding.bind(itemView)

        fun render(news: Noticias, onClickListener: (Noticias) -> Unit){
            // Get a non-default Storage bucket
            val storage = Firebase.storage.getReferenceFromUrl("gs://delegaapp.appspot.com/news/" + news.getImage().toString())
            storage.downloadUrl.addOnSuccessListener { url ->
                Log.i("URL: =>", url.toString())
                Glide.with(context)
                    .load(url)
                    .into(binding.imgPost)
            }.addOnFailureListener {
                Log.i("URL: =>", "No se encontró foto")
                binding.imgPost.visibility = View.GONE
            }


            binding.txtTitle.text = news.getTitle()
            binding.txtDescription.text = news.getDescription()

            binding.cvHolder.setOnClickListener{ onClickListener(news) }
        }
    }
}