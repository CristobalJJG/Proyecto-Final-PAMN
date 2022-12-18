package com.example.delegadosapp.vista.listaDelegados

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.delegadosapp.R
import com.example.delegadosapp.UsersCallback
import com.example.delegadosapp.databinding.DelegadoLayoutBinding
import com.example.delegadosapp.modelo.Usuario
import com.example.delegadosapp.vista.profile.ProfileActivity
import com.example.delegadosapp.vista.publications.log_usuario
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DelegadosAdapter(
    private val delegados: ArrayList<Usuario>, private val context: Context,
    private val onClickListener: (Usuario) -> Unit) : RecyclerView.Adapter<DelegadosAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context)
            return ViewHolder(v.inflate(R.layout.delegado_layout, parent, false))
        }

        override fun onBindViewHolder(vh: ViewHolder, i: Int) {
            vh.render(delegados[i], context, onClickListener)
        }

        override fun getItemCount(): Int { return delegados.size }

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val binding = DelegadoLayoutBinding.bind(itemView)

            fun render(delegado: Usuario, context: Context, onClickAction: (Usuario)->Unit){
                if(delegado.getPuesto() != "")binding.txtDelegaRol.text = delegado.getPuesto()
                else binding.txtDelegaRol.text = "Delegado"
                binding.txtUsername.text = delegado.getNombre()
                val storage = Firebase.storage.getReferenceFromUrl("gs://delegaapp.appspot.com/users/" + delegado.getImage())
                storage.downloadUrl.addOnSuccessListener { url ->
                    Log.i("URL: =>", url.toString())
                    Glide.with(context)
                        .load(url)
                        .into(binding.imgUserProfile)
                }.addOnFailureListener {
                    Log.i("URL: =>", "No se encontró foto")
                    binding.imgUserProfile.visibility = View.GONE
                }
                binding.cvDelegado.setOnClickListener{ onClickAction(delegado)}
            }
        }
}