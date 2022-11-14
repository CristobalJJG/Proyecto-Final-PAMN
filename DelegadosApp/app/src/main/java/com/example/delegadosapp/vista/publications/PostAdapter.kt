package com.example.delegadosapp.vista.publications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.delegadosapp.R

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    //Aquí realmente iría la llamada para reolectar los datos
    val titles = arrayOf(
        "Game Super Fest",
        "Asadero GII/GCID",
        "Partido de Padel",
        "Game Super Fest",
        "Asadero GII/GCID",
        "Partido de Padel"
    )
    val descriptions = arrayOf(
        "Sitio donde se podrán desarrollar distintos métodos de socialización, como pueden ser: \nJugar a videojuegos, cartas o juegos de mesa. \nVer películas y series. \nAmpliar conceptos de juegos a nivel general",
        "Sitio de socialización donde la gente beberá, comerá y socializará como si no hubiera un mañana.  Serán 10€ por persona, ya que tendremos que comprar comidita y ALCOHOL para que se puedan emborrachar.",
        "Partidito de Padel ejecutado por el Gran e Invencible Jose Manuel Illera Rodriguez (Siento si alguna parte de tu nombre está mal), se desarrollará un americano con todos los jugadores. Como premio habrá un abrazo dado por el mismísimo Jose Manuel Illera Rodríguez en persona. !Increíble¡",
        "Sitio donde se podrán desarrollar distintos métodos de socialización, como pueden ser: \nJugar a videojuegos, cartas o juegos de mesa. \nVer películas y series. \nAmpliar conceptos de juegos a nivel general",
        "Sitio de socialización donde la gente beberá, comerá y socializará como si no hubiera un mañana.  Serán 10€ por persona, ya que tendremos que comprar comidita y ALCOHOL para que se puedan emborrachar.",
        "Partidito de Padel ejecutado por el Gran e Invencible Jose Manuel Illera Rodriguez (Siento si alguna parte de tu nombre está mal), se desarrollará un americano con todos los jugadores. Como premio habrá un abrazo dado por el mismísimo Jose Manuel Illera Rodríguez en persona. !Increíble¡"
    )
    val images = arrayOf(
        R.drawable.default_picture,
        "",
        R.drawable.default_picture,
        "",
        "",
        R.drawable.default_picture
    )

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.post_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.title.text = titles[i]
        holder.description.text = descriptions[i]
        if(images[i]!= "") holder.img.setImageResource(images[i] as Int)
        else holder.img.visibility = View.GONE
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