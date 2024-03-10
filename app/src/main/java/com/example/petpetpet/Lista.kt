package com.example.petpetpet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petpetpet.adapter.AdaptadorMascota
import com.example.petpetpet.databinding.ListaActivityBinding


class Lista : AppCompatActivity() {
    private lateinit var binding: ListaActivityBinding


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListaActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val nombreUsuario = sharedPreferences.getString("username", "")

        binding.mostrarUsuario.text = "Usuario: $nombreUsuario"

        binding.btnVolver.setOnClickListener {
            val registro = Intent(this@Lista, RegistroAnimales::class.java)
            startActivity(registro)
        }

        initRecyclerView()
    }


    private fun initRecyclerView() {
        val db = BaseDatos(this)

        binding.MascotasRecycler.layoutManager = LinearLayoutManager(this)
        binding.MascotasRecycler.adapter = AdaptadorMascota(db.consultarTodasMascotas())
    }
}