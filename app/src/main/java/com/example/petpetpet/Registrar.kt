package com.example.petpetpet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.AltaUsuarioBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
// Clase Registrar
class Registrar : AppCompatActivity() {
    // Atributos de la clase
    private lateinit var binding: AltaUsuarioBinding
    //private lateinit var dbHelper: BaseDatos
    private var databaseUsuarios: DatabaseReference = FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app").getReference("Usuarios")

    // Método onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializar el binding
        binding = AltaUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el DBHelper
        //dbHelper = BaseDatos(this)
        //dbHelper.abrir()
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
            // Insertar el usuario en la base de datos
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

}