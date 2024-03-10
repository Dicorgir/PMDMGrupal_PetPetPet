package com.example.petpetpet

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petpetpet.adapter.AdaptadorMascota
import com.example.petpetpet.databinding.ListaActivityBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// Clase que permite a los usuarios consultar la lista de mascotas
class Lista : AppCompatActivity() {
    // Inicialización de variables
    private lateinit var binding: ListaActivityBinding
    private var databaseMascotas: DatabaseReference = FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app").getReference("Mascotas")

    // Función que se ejecuta al crear la actividad
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicialización de la vista
        binding = ListaActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtener el usuario y el tipo de usuario
        val sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val nombreUsuario = intent.getStringExtra("usuario").toString()
        val tipo = intent.getStringExtra("tipo").toString()
        // Mostrar el nombre del usuario
        binding.mostrarUsuario.text = "Usuario: $nombreUsuario"
        // Botón para volver a la pantalla de registro de animales
        binding.btnVolver.setOnClickListener {
            val registro = Intent(this@Lista, RegistroAnimales::class.java)
            registro.putExtra("usuario", nombreUsuario)
            registro.putExtra("tipo", tipo)
            startActivity(registro)
        }
        // Inicializar el RecyclerView
        initRecyclerView()
    }


// Función que inicializa el RecyclerView
private fun initRecyclerView() {
    // Referencia a la base de datos
    val database = FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app")
    val reference = database.getReference("Mascotas")
    val mascotaList = mutableListOf<Mascota>()

    // Obtener las mascotas de la base de datos
    reference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (mascotaSnapshot in dataSnapshot.children) {
                val mascota = mascotaSnapshot.getValue(Mascota::class.java)
                if (mascota != null) {
                    mascotaList.add(mascota)
                }
            }
            // Mostrar las mascotas en el RecyclerView
            binding.MascotasRecycler.layoutManager = LinearLayoutManager(this@Lista)
            binding.MascotasRecycler.adapter = AdaptadorMascota(mascotaList)
        }
        // Función que se ejecuta al obtener el valor
        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(TAG, "Error reading Mascotas", databaseError.toException())
        }
    })
    }

}