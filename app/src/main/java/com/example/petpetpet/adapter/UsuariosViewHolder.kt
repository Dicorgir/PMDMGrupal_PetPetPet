package com.example.petpetpet.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.Usuario
import com.example.petpetpet.databinding.ViewholderUsuariosBinding
// Clase que se encarga de mostrar los datos de los usuarios en el RecyclerView
class UsuariosViewHolder(view: View): RecyclerView.ViewHolder(view){
    // Se enlaza el layout con el ViewHolder
    val binding = ViewholderUsuariosBinding.bind(view)
    // Función que se encarga de mostrar los datos de los usuarios en el RecyclerView
    fun render(usuario: Usuario){
        // Se asignan los valores de los datos del usuario a los elementos del layout
        binding.nombreCompletoUsuario.text = usuario.nombreCompleto
        binding.nombreUsuario.text = usuario.nombreUsuario
        binding.adminousuario.text = usuario.tipo
        binding.estado.text = usuario.estado

    }
}