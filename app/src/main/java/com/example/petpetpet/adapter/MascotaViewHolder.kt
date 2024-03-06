package com.example.petpetpet.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.Mascota
import com.bumptech.glide.Glide
import com.example.petpetpet.databinding.ViewholderMascotaBinding


class MascotaViewHolder(view: View): RecyclerView.ViewHolder(view){
    val binding = ViewholderMascotaBinding.bind(view)

    fun render(mascota: Mascota){
        binding.nombrePerro.text = mascota.nombre
        binding.identificadorPerro.text = mascota.id.toString()
        Glide.with(binding.fotito.context).load(mascota.imagen).into(binding.fotito)
    }
}