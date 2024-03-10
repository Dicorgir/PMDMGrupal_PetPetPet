package com.example.petpetpet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petpetpet.adapter.AdaptadorMascota
import com.example.petpetpet.databinding.ListaActivityBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Lista : AppCompatActivity() {
    private lateinit var binding: ListaActivityBinding
    private var databaseMascotas: DatabaseReference = FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app").getReference("Mascotas")


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
        databaseMascotas.child("Mascotas").get().addOnSuccessListener {
            if (it.exists()) {
                val mascotas = mutableListOf<Mascota>()
                for (snapshot in it.children) {
                    val nombreMascota = snapshot.key.toString()
                    val imagen = snapshot.child("imagen").value.toString()
                    val raza = snapshot.child("raza").value.toString()
                    val sexo = snapshot.child("sexo").value.toString()
                    val fechaNacimiento = snapshot.child("fechaNacimiento").value.toString()
                    val dni = snapshot.child("dni").value.toString()
                    val mascota = Mascota(nombreMascota,imagen, raza, sexo, fechaNacimiento, dni)
                    mascotas.add(mascota)
                }
                binding.MascotasRecycler.layoutManager = LinearLayoutManager(this)
                binding.MascotasRecycler.adapter = AdaptadorMascota(mascotas)
            }
        }
    }
}