package com.example.petpetpet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.Mascota
import com.example.petpetpet.R
// Clase que se encarga de adaptar la lista de mascotas a la vista
class AdaptadorMascota(private val mascotaList: List<Mascota>) :
    RecyclerView.Adapter<MascotaViewHolder>() {
    // Se encarga de crear la vista de la mascota
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        // Se infla la vista de la mascota
        val layoutInflater = LayoutInflater.from(parent.context)
        // Se retorna la vista de la mascota
        return MascotaViewHolder(layoutInflater.inflate(R.layout.viewholder_mascota, parent, false))
    }
    // Se encarga de mostrar la mascota
    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        // Se obtiene la mascota
        val item = mascotaList[position]
        // Se muestra la mascota
        holder.render(item)
    }
    // Se encarga de obtener la cantidad de mascotas
    override fun getItemCount(): Int {
        // Se retorna la cantidad de mascotas
        return mascotaList.size
    }
}