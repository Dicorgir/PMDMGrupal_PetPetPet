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


class Lista : AppCompatActivity() {
    private lateinit var binding: ListaActivityBinding
    private var databaseMascotas: DatabaseReference = FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app").getReference("Mascotas")


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListaActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val nombreUsuario = intent.getStringExtra("usuario").toString()
        val tipo = intent.getStringExtra("tipo").toString()

        binding.mostrarUsuario.text = "Usuario: $nombreUsuario"

        binding.btnVolver.setOnClickListener {
            val registro = Intent(this@Lista, RegistroAnimales::class.java)
            registro.putExtra("usuario", nombreUsuario)
            registro.putExtra("tipo", tipo)
            startActivity(registro)
        }

        initRecyclerView()
    }



private fun initRecyclerView() {
    val database = FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app")
    val reference = database.getReference("Mascotas")
    val mascotaList = mutableListOf<Mascota>()

    reference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (mascotaSnapshot in dataSnapshot.children) {
                val mascota = mascotaSnapshot.getValue(Mascota::class.java)
                if (mascota != null) {
                    mascotaList.add(mascota)
                }
            }
            binding.MascotasRecycler.layoutManager = LinearLayoutManager(this@Lista)
            binding.MascotasRecycler.adapter = AdaptadorMascota(mascotaList)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w(TAG, "Error reading Mascotas", databaseError.toException())
        }
    })
    }

}