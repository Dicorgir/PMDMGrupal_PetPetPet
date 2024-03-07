package com.example.petpetpet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petpetpet.adapter.AdaptadorUsuarios
import com.example.petpetpet.databinding.ListaUsuariosBinding

class ListaUsuarios : AppCompatActivity() {
    private lateinit var binding: ListaUsuariosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListaUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val usuarios = obtenerUsuarios()

        binding.recyclerUsuarios.layoutManager = LinearLayoutManager(this)
        binding.recyclerUsuarios.adapter = AdaptadorUsuarios(usuarios)
    }

    private fun obtenerUsuarios(): List<Usuario> {
        // Aquí debes implementar la lógica para obtener la lista de usuarios
        // Por ahora, devolveremos una lista vacía
        return emptyList()
    }
}