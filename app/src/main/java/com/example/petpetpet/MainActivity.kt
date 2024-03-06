package com.example.petpetpet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.BaseDatos
import com.example.petpetpet.MainActivity2
import com.example.petpetpet.MainActivity3
import com.example.petpetpet.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: BaseDatos // Suponiendo que tienes una clase BaseDatos para manejar la base de datos
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BaseDatos(this) // Inicializar el objeto BaseDatos
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        // Verificar si el usuario y la contraseña deben recordarse
        val rememberMeChecked = sharedPreferences.getBoolean("rememberMe", false)
        if (rememberMeChecked) {
            val savedUsername = sharedPreferences.getString("username", "")
            val savedPassword = sharedPreferences.getString("password", "")
            binding.CuadroNombre.setText(savedUsername)
            binding.cuadroContra.setText(savedPassword)
            binding.switch1.isChecked = true
        }

        binding.BotonLogin.setOnClickListener {
            val usuarioIngresado = binding.CuadroNombre.text.toString()
            val contrasenaIngresada = binding.cuadroContra.text.toString()
            val rememberMeChecked = binding.switch1.isChecked

            if (rememberMeChecked) {
                // Si "Recuérdame" está marcado, guardar usuario y contraseña en SharedPreferences
                with(sharedPreferences.edit()) {
                    putBoolean("rememberMe", true)
                    putString("username", usuarioIngresado)
                    putString("password", contrasenaIngresada)
                    apply()
                }
            } else {
                // Si no está marcado, limpiar los valores guardados en SharedPreferences
                with(sharedPreferences.edit()) {
                    remove("rememberMe")
                    remove("username")
                    remove("password")
                    apply()
                }
            }

            if (validarCredenciales(usuarioIngresado, contrasenaIngresada)) {
                val intent = Intent(this, MainActivity3::class.java)
                startActivity(intent)
            } else {
                Snackbar.make(binding.root, "Usuario o contraseña no son correctos", Snackbar.LENGTH_SHORT).show()
                // Limpiar los campos de texto
                binding.CuadroNombre.text?.clear()
                binding.cuadroContra.text?.clear()

                // Devolver el foco al campo de usuario
                binding.CuadroNombre.requestFocus()
            }
        }
        // Botón de registro
        binding.botonDarAlta.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }


    private fun validarCredenciales(usuario: String, contrasena: String): Boolean {
        // Consultar la base de datos para verificar las credenciales
        return db.checkUserAndPassword(usuario, contrasena)
    }
}
