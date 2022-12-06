package com.example.delegadosapp.vista.listaDelegados

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.R
import com.example.delegadosapp.databinding.DelegadoLayoutBinding
import com.example.delegadosapp.modelo.Usuario

class DelegadosAdapter (
    private val delegados: ArrayList<Usuario>,
    private val onClickListener: (Usuario) -> Unit) : RecyclerView.Adapter<DelegadosAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.delegado_layout, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(vh: ViewHolder, i: Int) {
            vh.render(delegados[i], onClickListener)
        }

        override fun getItemCount(): Int { return delegados.size }

        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val binding = DelegadoLayoutBinding.bind(itemView)

            fun render(delegado: Usuario, onClickAction: (Usuario)->Unit){
                binding.txtDelegaRol.text = delegado.getRol().toString()
                binding.txtUsername.text = delegado.getNombre()
                //binding.txtDelegaRol.img = delegado.getImage()
            }
        }
}