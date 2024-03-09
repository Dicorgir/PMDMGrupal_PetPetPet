package com.example.petpetpet.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.Mascota
import com.example.petpetpet.VerAnimal
import com.bumptech.glide.Glide
import com.example.petpetpet.databinding.ViewholderMascotaBinding

class MascotaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ViewholderMascotaBinding.bind(view)

    fun render(mascota: Mascota) {
        binding.nombrePerro.text = mascota.nombre
        binding.identificadorPerro.text = mascota.id.toString()
        Glide.with(binding.fotito.context).load(mascota.imagen).into(binding.fotito)

        binding.btnVerAnimal.setOnClickListener {
            val context = it.context
            val intent = Intent(context, VerAnimal::class.java).apply {
                putExtra("cod", mascota.id)
                putExtra("nombre", mascota.nombre)
                putExtra("raza", mascota.raza)
                putExtra("fecha_nacimiento", mascota.fechaNacimiento.time) // Convertir a Long
                putExtra("sexo", mascota.sexo)
                putExtra("dni", mascota.dni)
                putExtra("foto", mascota.imagen)
            }
            context.startActivity(intent)
        }
    }
}