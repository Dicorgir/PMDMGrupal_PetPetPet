package com.example.petpetpet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.Mascota
import com.example.petpetpet.R

class AdaptadorMascota(private val mascotaList: List<Mascota>) :
    RecyclerView.Adapter<MascotaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MascotaViewHolder(layoutInflater.inflate(R.layout.viewholder_mascota, parent, false))
    }

    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        val item = mascotaList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return mascotaList.size
    }
}