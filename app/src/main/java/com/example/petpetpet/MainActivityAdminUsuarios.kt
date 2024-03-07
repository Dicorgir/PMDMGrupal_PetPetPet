package com.example.petpetpet

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.PestanaAdministrarUsuariosBinding
import com.google.firebase.database.*

class MainActivityAdminUsuarios : AppCompatActivity() {
    private lateinit var binding: PestanaAdministrarUsuariosBinding
    private var databaseUsuarios: DatabaseReference =
        FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app").getReference("Usuarios")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PestanaAdministrarUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botonConsultarTodosUsuarios.setOnClickListener {
            val intent = Intent(this, ListaUsuarios::class.java)
            startActivity(intent)
        }

        binding.botonBorrarUsuarios.setOnClickListener {
            val nombreUsuario = binding.cuadroUsuarioUsur.text.toString().trim()

            if (nombreUsuario.isNotEmpty()) {
                val referenciaUsuario = databaseUsuarios.child(nombreUsuario)

                referenciaUsuario.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            referenciaUsuario.removeValue()
                            Toast.makeText(
                                this@MainActivityAdminUsuarios,
                                "Usuario eliminado exitosamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@MainActivityAdminUsuarios,
                                "El usuario no existe",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Maneja el error si es necesario
                    }
                })
            } else {
                Toast.makeText(
                    this@MainActivityAdminUsuarios,
                    "Ingrese un nombre de usuario válido",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
