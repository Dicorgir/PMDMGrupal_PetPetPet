package com.example.petpetpet.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.Mascota
import com.example.petpetpet.VerAnimal
import com.bumptech.glide.Glide
import com.example.petpetpet.databinding.ViewholderMascotaBinding
// Clase que se encarga de adaptar los datos de las mascotas a la vista
class MascotaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Se encarga de mostrar los datos de las mascotas
    val binding = ViewholderMascotaBinding.bind(view)
    // Se encarga de mostrar los datos de las mascotas
    fun render(mascota: Mascota) {
        // Se muestra la mascota
        binding.nombrePerro.text = mascota.nombre
        binding.identificadorPerro.text = mascota.id.toString()
        // Se muestra la imagen de la mascota
        Glide.with(binding.fotito.context).load(mascota.imagen).into(binding.fotito)
        // Se muestra el botón para ver la mascota
        binding.btnVerAnimal.setOnClickListener {
            // Se obtiene el contexto
            val context = it.context
            // Se crea el intent para ver la mascota
            val intent = Intent(context, VerAnimal::class.java).apply {
                putExtra("cod", mascota.id)
                putExtra("nombre", mascota.nombre)
                putExtra("raza", mascota.raza)
                putExtra("fecha_nacimiento", mascota.fechaNacimiento.time) // Convertir a Long
                putExtra("sexo", mascota.sexo)
                putExtra("dni", mascota.dni)
                putExtra("foto", mascota.imagen)
            }
            // Se inicia la actividad para ver la mascota
            context.startActivity(intent)
        }
    }
}