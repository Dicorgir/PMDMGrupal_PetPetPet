package com.example.petpetpet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.Usuario
import com.example.petpetpet.R
// Clase que se encarga de adaptar los datos de los usuarios a la vista
class AdaptadorUsuarios(private val usuarioList: List<Usuario>) :
    RecyclerView.Adapter<UsuariosViewHolder>() {
    // Se encarga de crear la vista de los usuarios
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuariosViewHolder {
        // Se infla la vista de los usuarios
        val layoutInflater = LayoutInflater.from(parent.context)
        // Se retorna la vista de los usuarios
        return UsuariosViewHolder(layoutInflater.inflate(R.layout.viewholder_usuarios, parent, false))
    }
    // Se encarga de mostrar los datos de los usuarios
    override fun onBindViewHolder(holder: UsuariosViewHolder, position: Int) {
        // Se obtiene el usuario
        val item = usuarioList[position]
        // Se muestra el usuario
        holder.render(item)
    }
    // Se encarga de obtener la cantidad de usuarios
    override fun getItemCount(): Int {
        // Se retorna la cantidad de usuarios
        return usuarioList.size
    }
}