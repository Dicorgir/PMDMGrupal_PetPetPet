package com.example.petpetpet

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.AltaUsuarioBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: AltaUsuarioBinding

    private var databaseUsuarios: DatabaseReference = FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app").getReference("Usuarios")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AltaUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el DBHelper
        //dbHelper = BaseDatos(this)

        binding.botonRegistrarUsuario.setOnClickListener{
            // Obtener los valores de los cuadros de texto
            val nombreUsuario = binding.CuadroUsuario.text.toString()
            val nombreCompleto = binding.CuadroNombre.text.toString()
            val contrasena = binding.cuadroContra.text.toString()

            // Verificar que los campos no estén vacíos
            if (nombreUsuario.isEmpty() || nombreCompleto.isEmpty() || contrasena.isEmpty()) {
                Snackbar.make(it, "Por favor, llene todos los campos", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insertar el usuario en la base de datos
            val usuario = Usuario(nombreUsuario, nombreCompleto, contrasena, "usuario", "activo")
            databaseUsuarios.child(nombreUsuario).setValue(usuario)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}