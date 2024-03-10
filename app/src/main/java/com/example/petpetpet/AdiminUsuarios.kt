package com.example.petpetpet

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petpetpet.databinding.PestanaAdministrarUsuariosBinding
import com.google.firebase.database.*
// Clase que permite a los administradores modificar, consultar y eliminar usuarios
class AdiminUsuarios : AppCompatActivity() {
    // Inicialización de variables
    private lateinit var binding: PestanaAdministrarUsuariosBinding
    // Referencia a la base de datos
    private var databaseUsuarios: DatabaseReference =
        FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app").getReference("Usuarios")
    // Función que se ejecuta al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicialización de la vista
        binding = PestanaAdministrarUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtener el usuario y el tipo de usuario
        val usuario = intent.getStringExtra("usuario").toString()
        val tipo = intent.getStringExtra("tipo").toString()

        // Verificar que el usuario sea administrador
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
        // Botón para volver a la pantalla de registro de animales
        binding.botonVolverUsuarios.setOnClickListener {
            val registro = Intent(this@AdiminUsuarios, RegistroAnimales::class.java)
            registro.putExtra("usuario", usuario)
            registro.putExtra("tipo", tipo)
            startActivity(registro)
        }
    }
    // Función que elimina un usuario de la base de datos
    private fun eliminarUsuario(nombreUsuario: String) {
        if (nombreUsuario.isNotEmpty()) {
            // Referencia al usuario en la base de datos
            val referenciaUsuario = databaseUsuarios.child(nombreUsuario)
            // Eliminar el usuario
            referenciaUsuario.addListenerForSingleValueEvent(object : ValueEventListener {
                // Función que se ejecuta al obtener el valor
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
            // Mostrar mensaje si el nombre de usuario es inválido
            mostrarMensaje("Ingrese un nombre de usuario válido")
        }
    }
    // Función que modifica un usuario en la base de datos
    private fun modificarUsuario(nombreUsuario: String) {
        // Verificar que el nombre de usuario no esté vacío
        if (nombreUsuario.isNotEmpty()) {
            val referenciaUsuario = databaseUsuarios.child(nombreUsuario)
            // Verificar que el usuario exista
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
                        // Mostrar mensaje de éxito
                        mostrarMensaje("Usuario modificado exitosamente")
                    } else {
                        // Mostrar mensaje si el usuario no existe
                        mostrarMensaje("El usuario no existe")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Maneja el error si es necesario
                }
            })
        } else {
            // Mostrar mensaje si el nombre de usuario es inválido
            mostrarMensaje("Ingrese un nombre de usuario válido")
        }
    }
    // Función que consulta y muestra un usuario de la base de datos
    private fun consultarYMostrarUsuario(nombreUsuario: String) {
        // Verificar que el nombre de usuario no esté vacío
        if (nombreUsuario.isNotEmpty()) {
            // Referencia al usuario en la base de datos
            val referenciaUsuario = databaseUsuarios.child(nombreUsuario)
            // Verificar que el usuario exista
            referenciaUsuario.addListenerForSingleValueEvent(object : ValueEventListener {
                // Función que se ejecuta al obtener el valor
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
                        // Mostrar mensaje si el usuario no existe
                        mostrarMensaje("El usuario no existe")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Maneja el error si es necesario
                }
            })
        } else {
            // Mostrar mensaje si el nombre de usuario es inválido
            mostrarMensaje("Ingrese un nombre de usuario válido")
        }
    }



    // Función que muestra un mensaje en pantalla
    private fun mostrarMensaje(mensaje: String) {
        // Mostrar mensaje
        Toast.makeText(
            this@AdiminUsuarios,
            mensaje,
            Toast.LENGTH_SHORT
        ).show()
    }
}
