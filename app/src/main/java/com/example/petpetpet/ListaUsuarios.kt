package com.example.petpetpet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.ListaUsuariosBinding

class ListaUsuarios : AppCompatActivity() {
    private lateinit var binding: ListaUsuariosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListaUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}