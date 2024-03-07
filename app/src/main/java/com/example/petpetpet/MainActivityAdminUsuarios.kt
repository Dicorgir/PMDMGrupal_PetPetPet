package com.example.petpetpet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.PestanaAdministrarUsuariosBinding

class MainActivityAdminUsuarios : AppCompatActivity() {
    private lateinit var binding: PestanaAdministrarUsuariosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PestanaAdministrarUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonConsultarTodosUsuarios.setOnClickListener {
            val intent = Intent(this, ListaUsuarios::class.java)
            startActivity(intent)
        }
    }
}