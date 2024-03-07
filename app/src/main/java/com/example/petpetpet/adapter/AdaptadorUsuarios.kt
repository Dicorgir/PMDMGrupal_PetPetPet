package com.example.petpetpet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petpetpet.Usuario
import com.example.petpetpet.R

class AdaptadorUsuarios(private val usuarioList: List<Usuario>) :
    RecyclerView.Adapter<UsuariosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuariosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UsuariosViewHolder(layoutInflater.inflate(R.layout.viewholder_usuarios, parent, false))
    }

    override fun onBindViewHolder(holder: UsuariosViewHolder, position: Int) {
        val item = usuarioList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return usuarioList.size
    }
}