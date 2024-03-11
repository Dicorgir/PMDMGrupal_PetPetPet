package com.example.petpetpet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petpetpet.adapter.AdaptadorUsuarios
import com.example.petpetpet.databinding.ListaUsuariosBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
// Clase que muestra la lista de usuarios
class ListaUsuarios : AppCompatActivity() {
    // Inicialización de variables
    private lateinit var binding: ListaUsuariosBinding
    // Referencia a la base de datos
    private var databaseUsuarios: DatabaseReference = FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app").getReference("Usuarios")
    // Función que se ejecuta al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicialización de la vista
        binding = ListaUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Configura el RecyclerView
        initRecyclerView()
    }
    // Función que inicializa el RecyclerView
    private fun initRecyclerView() {
        // Obtiene la lista de usuarios
        val usuarios = obtenerUsuarios()
        // Configura el RecyclerView con la lista de usuarios obtenida
        binding.recyclerUsuarios.layoutManager = LinearLayoutManager(this)
        binding.recyclerUsuarios.adapter = AdaptadorUsuarios(usuarios)
    }
    // Función que obtiene la lista de usuarios
    private fun obtenerUsuarios(): List<Usuario> {
        // Lista de usuarios
        val usuarios = mutableListOf<Usuario>()

        // Escucha cambios en la base de datos para obtener la lista de usuarios
        databaseUsuarios.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    // Obtén los datos de cada usuario
                    val nombreUsuario = snapshot.key.toString()
                    val estado = snapshot.child("estado").value.toString()
                    val nombreCompleto = snapshot.child("nombreCompleto").value.toString()
                    val tipo = snapshot.child("tipo").value.toString()

                    // Crea un objeto Usuario con los datos obtenidos
                    val usuario = Usuario(nombreUsuario,nombreCompleto,"",tipo,estado)

                    // Agrega el usuario a la lista
                    usuarios.add(usuario)
                }

                // Configura el RecyclerView con la lista de usuarios obtenida
                binding.recyclerUsuarios.layoutManager = LinearLayoutManager(this@ListaUsuarios)
                binding.recyclerUsuarios.adapter = AdaptadorUsuarios(usuarios)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Maneja el error si es necesario
            }
        })

        return usuarios
    }
}
