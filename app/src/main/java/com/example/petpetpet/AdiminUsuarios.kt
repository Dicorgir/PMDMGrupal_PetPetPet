package com.example.petpetpet

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.PestanaAdministrarUsuariosBinding
import com.google.firebase.database.*

class AdiminUsuarios : AppCompatActivity() {
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
            eliminarUsuario(nombreUsuario)
        }
        binding.botonModificarUsuario.setOnClickListener {
            val nombreUsuario = binding.cuadroUsuarioUsur.text.toString().trim()
            modificarUsuario(nombreUsuario)
        }
        binding.botonConsultarUsuario.setOnClickListener {
            val nombreUsuario = binding.cuadroUsuarioUsur.text.toString().trim()
            consultarYMostrarUsuario(nombreUsuario)
        }
    }

    private fun eliminarUsuario(nombreUsuario: String) {
        if (nombreUsuario.isNotEmpty()) {
            val referenciaUsuario = databaseUsuarios.child(nombreUsuario)

            referenciaUsuario.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        referenciaUsuario.removeValue()
                        mostrarMensaje("Usuario eliminado exitosamente")
                    } else {
                        mostrarMensaje("El usuario no existe")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Maneja el error si es necesario
                }
            })
        } else {
            mostrarMensaje("Ingrese un nombre de usuario válido")
        }
    }

    private fun modificarUsuario(nombreUsuario: String) {
        if (nombreUsuario.isNotEmpty()) {
            val referenciaUsuario = databaseUsuarios.child(nombreUsuario)

            referenciaUsuario.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Obtener el estado de los Switches
                        val switchTipo = binding.switch4.isChecked
                        val switchEstado = binding.switchAltaBaja.isChecked

                        // Obtener los valores de los cuadros de texto
                        val nuevoNombreCompleto = binding.cuadroNombreCompleto.text.toString().trim()
                        val nuevoNombreUsuario = binding.cuadroUsuarioUsur.text.toString().trim()

                        // Asignar valores según el estado de los Switches y los cuadros de texto
                        val nuevoTipo = if (switchTipo) "administrador" else "usuario"
                        val nuevoEstado = if (switchEstado) "activo" else "baja"

                        // Actualizar los valores en la base de datos
                        referenciaUsuario.child("nombreCompleto").setValue(nuevoNombreCompleto)
                        referenciaUsuario.child("nombreUsuario").setValue(nuevoNombreUsuario)
                        referenciaUsuario.child("tipo").setValue(nuevoTipo)
                        referenciaUsuario.child("estado").setValue(nuevoEstado)

                        mostrarMensaje("Usuario modificado exitosamente")
                    } else {
                        mostrarMensaje("El usuario no existe")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Maneja el error si es necesario
                }
            })
        } else {
            mostrarMensaje("Ingrese un nombre de usuario válido")
        }
    }
    private fun consultarYMostrarUsuario(nombreUsuario: String) {
        if (nombreUsuario.isNotEmpty()) {
            val referenciaUsuario = databaseUsuarios.child(nombreUsuario)

            referenciaUsuario.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Obtener los valores de la base de datos
                        val nombreCompleto = dataSnapshot.child("nombreCompleto").value.toString()
                        val nombreUsuario = dataSnapshot.child("nombreUsuario").value.toString()
                        val tipo = dataSnapshot.child("tipo").value.toString()
                        val estado = dataSnapshot.child("estado").value.toString()

                        // Mostrar los valores en los cuadros de texto y switches
                        binding.cuadroNombreCompleto.setText(nombreCompleto)
                        binding.cuadroUsuarioUsur.setText(nombreUsuario)

                        binding.switch4.isChecked = tipo == "administrador"
                        binding.switchAltaBaja.isChecked = estado == "activo"
                    } else {
                        mostrarMensaje("El usuario no existe")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Maneja el error si es necesario
                }
            })
        } else {
            mostrarMensaje("Ingrese un nombre de usuario válido")
        }
    }




    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(
            this@AdiminUsuarios,
            mensaje,
            Toast.LENGTH_SHORT
        ).show()
    }
}
