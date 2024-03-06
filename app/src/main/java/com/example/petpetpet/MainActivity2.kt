package com.example.petpetpet

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.AltaUsuarioBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: AltaUsuarioBinding
    private lateinit var dbHelper: BaseDatos // Debes tener una instancia de tu BaseDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AltaUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el DBHelper
        dbHelper = BaseDatos(this)

        binding.botonRegistrarUsuario.setOnClickListener{
            // Obtener los valores de los cuadros de texto
            val nombreUsuario = binding.CuadroUsuario.text.toString()
            val nombreCompleto = binding.CuadroNombre.text.toString()
            val contrasena = binding.cuadroContra.text.toString()

            // Insertar los datos en la base de datos
            if (insertarUsuario(nombreUsuario, nombreCompleto, contrasena)) {
                Snackbar.make(binding.root, "Usuario registrado correctamente", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, "Error al registrar usuario", Snackbar.LENGTH_SHORT).show()
            }
            // Regresar al MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun insertarUsuario(nombreUsuario: String, nombreCompleto: String, contrasena: String): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(BaseDatos.COLUMN_USERNAME, nombreUsuario)
            put(BaseDatos.COLUMN_NAME, nombreCompleto)
            put(BaseDatos.COLUMN_PASSWORD, contrasena)
        }
        val newRowId = db.insert(BaseDatos.TABLE_USUARIO, null, values)
        return newRowId != -1L
    }
}