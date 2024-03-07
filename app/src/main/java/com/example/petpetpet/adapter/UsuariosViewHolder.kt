package com.example.petpetpet.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.Usuario
import com.example.petpetpet.databinding.ViewholderUsuariosBinding

class UsuariosViewHolder(view: View): RecyclerView.ViewHolder(view){
    val binding = ViewholderUsuariosBinding.bind(view)

    fun render(usuario: Usuario){
        binding.nombreCompletoUsuario.text = usuario.nombreCompleto
        binding.nombreUsuario.text = usuario.nombreUsuario
        binding.textoAdminSiONo.text = if (usuario.isAdmin) "Sí" else "No"
    }
}