package com.example.petpetpet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {
    // Inicializar la base de datos de Firebase
    private lateinit var binding: ActivityMainBinding
    // Inicializar SharedPreferences
    private lateinit var sharedPreferences: SharedPreferences
    // Inicializar la base de datos de Firebase
    private var databaseUsuarios: DatabaseReference =
        FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("Usuarios")
    // Inicializar el DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializar la vista
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        // Verificar si el usuario y la contraseña deben recordarse
        val rememberMeChecked = sharedPreferences.getBoolean("rememberMe", false)
        // Si se debe recordar, rellenar los campos de usuario y contraseña
        if (rememberMeChecked) {
            // Obtener el usuario y la contraseña guardados
            val savedUsername = sharedPreferences.getString("username", "")
            // Obtener el usuario y la contraseña guardados
            val savedPassword = sharedPreferences.getString("password", "")
            // Rellenar los campos de usuario y contraseña
            binding.CuadroNombre.setText(savedUsername)
            binding.cuadroContra.setText(savedPassword)
            // Marcar el interruptor "Recuérdame"
            binding.switch1.isChecked = true
        }
        // Botón de inicio de sesión
        binding.BotonLogin.setOnClickListener {
            // Obtener el usuario y la contraseña ingresados
            val usuarioIngresado = binding.CuadroNombre.text.toString()
            val contrasenaIngresada = binding.cuadroContra.text.toString()
            // Verificar si "Recuérdame" está marcado
            val rememberMeChecked = binding.switch1.isChecked
            // Verificar si el usuario y la contraseña no están vacíos
            if (rememberMeChecked) {
                // Si "Recuérdame" está marcado, guardar usuario y contraseña en SharedPreferences
                with(sharedPreferences.edit()) {
                    putBoolean("rememberMe", true)
                    putString("username", usuarioIngresado)
                    putString("password", contrasenaIngresada)
                    apply()
                }
            }
            // Si "Recuérdame" no está marcado, limpiar los valores guardados en SharedPreferences
            else {
                // Si no está marcado, limpiar los valores guardados en SharedPreferences
                with(sharedPreferences.edit()) {
                    remove("rememberMe")
                    remove("username")
                    remove("password")
                    apply()
                }
            }
            // Verificar si el usuario y la contraseña no están vacíos
            databaseUsuarios.child(usuarioIngresado).child(usuarioIngresado).get()
                .addOnSuccessListener {
                    // Verificar si el usuario existe
                    databaseUsuarios.child(usuarioIngresado).child("contrasenaval").get()
                        .addOnSuccessListener {
                            // Verificar si la contraseña es correcta
                            val contrasenaReal = it.value.toString()
                            if (contrasenaReal == contrasenaIngresada) {
                                // Verificar si el usuario está activo
                                databaseUsuarios.child(usuarioIngresado).child("estado").get()
                                    .addOnSuccessListener { dataSnapshot ->
                                        // Verificar si el usuario está activo
                                        if (dataSnapshot.exists()) {
                                            // Verificar si el usuario está activo
                                            val estado = dataSnapshot.value.toString()
                                            if (estado == "activo") {
                                                // Verificar si el usuario es un administrador
                                                databaseUsuarios.child(usuarioIngresado)
                                                    .child("tipo").get()
                                                    .addOnSuccessListener { dataSnapshot ->
                                                        // Verificar si el usuario es un administrador
                                                        if (dataSnapshot.exists()) {
                                                            // Verificar si el usuario es un administrador
                                                            val tipo = dataSnapshot.value.toString()
                                                            val intent = Intent(
                                                                this,
                                                                RegistroAnimales::class.java
                                                            )
                                                            // Pasar el usuario y el tipo a la siguiente actividad
                                                            intent.putExtra(
                                                                "usuario",
                                                                usuarioIngresado
                                                            )
                                                            // Pasar el usuario y el tipo a la siguiente actividad
                                                            intent.putExtra("tipo", tipo)
                                                            startActivity(intent)
                                                            Toast.makeText(
                                                                this@Login,
                                                                "Conectado con Exito",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                            // Limpiar los campos de texto
                                                        } else {
                                                            fallo()
                                                        }
                                                    }.addOnCanceledListener {
                                                    fallo()
                                                }
                                            } else {
                                                fallo()
                                            }
                                        } else {
                                            fallo()
                                        }
                                        // Limpiar los campos de texto
                                    }.addOnCanceledListener {
                                    fallo()
                                }
                            } else {
                                fallo()
                            }
                        }.addOnCanceledListener {
                        fallo()
                    }
                }.addOnCanceledListener {
                fallo()
            }

        }
        // Botón de registro
        binding.botonDarAlta.setOnClickListener {
            // Ir a la actividad de registro
            val intent = Intent(this, Registrar::class.java)
            startActivity(intent)
        }
    }
    // Función para manejar el fallo de inicio de sesión
    private fun fallo() {
        Toast.makeText(this, "Error al conectar", Toast.LENGTH_SHORT).show()
        // Limpiar los campos de texto
        binding.CuadroNombre.text?.clear()
        binding.cuadroContra.text?.clear()

        // Devolver el foco al campo de usuario
        binding.CuadroNombre.requestFocus()
    }

}
