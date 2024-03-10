package com.example.petpetpet

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.petpetpet.databinding.PestanaRegistroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Locale

class RegistroAnimales : AppCompatActivity() {
    private lateinit var binding: PestanaRegistroBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var rutaImagen: String =
        "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/65761296352685.5eac4787a4720.jpg" // Inicializa la variable con una cadena vacía
    private var databaseUsuarios: DatabaseReference =
        FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("Usuarios")
    private var databaseMascotas: DatabaseReference =
        FirebaseDatabase.getInstance("https://petpetpet-2460d-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("Mascotas")

    companion object {
        private const val CODIGO_SELECCION_IMAGEN = 100
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PestanaRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        // Recuperar el nombre de usuario del SharedPreferences
        val username = intent.getStringExtra("usuario").toString()
        val tipo = intent.getStringExtra("tipo").toString()

        if (tipo == "usuario") {
            binding.buttonAdministrarUsuarios.isVisible = false
            binding.botonAlta.isVisible = false
            binding.botonModifica.isVisible = false
            binding.botonBorrar.isVisible = false
            binding.botonConsulta.isVisible = false

            binding.textView10.isVisible = false
            binding.textView4.isVisible = false
            binding.cuadroIdenti.isVisible = false
            binding.cuadroNombre.isVisible = false
            binding.cuadroRaza.isVisible = false
            binding.cuadroSexo.isVisible = false
            binding.cuadroNac.isVisible = false
            binding.cuadroDNI.isVisible = false
            binding.textView6.isVisible = false
            binding.textView7.isVisible = false
            binding.textView8.isVisible = false
            binding.textView10.isVisible = false
            binding.textView11.isVisible = false
            binding.imagenGaler.isVisible = false


        }

        // Mostrar el nombre de usuario en el TextView
        binding.textoUsuario.text = "Usuario: $username"

        // Agregar listener al botón "elegir imagen"

        binding.botonAlta.setOnClickListener {

            // Verifica si alguno de los campos está vacío
            if (binding.cuadroIdenti.text.isEmpty() ||
                binding.cuadroNombre.text.isEmpty() ||
                binding.cuadroRaza.text.isEmpty() ||
                binding.cuadroSexo.text.isEmpty() ||
                binding.cuadroNac.text.isEmpty() ||
                binding.cuadroDNI.text.isEmpty()
            ) {
                // Muestra un Snackbar indicando que se deben completar todos los campos
                Snackbar.make(
                    binding.root,
                    "Por favor, completa todos los campos",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val formato = SimpleDateFormat("dd-MM-yyyy")
                val mascota = formato.parse(binding.cuadroNac.text.toString())?.let {
                    val nombre = binding.cuadroNombre.text.toString()
                    val raza = binding.cuadroRaza.text.toString()
                    val imagen = when (raza.toLowerCase(Locale.ROOT)) {
                        "husky" -> "https://bowwowinsurance.com.au/wp-content/uploads/2020/09/shutterstock_15250126-Alaskan-Husky-in-front-of-white-background-thumbnail.jpg"
                        "staffordshire terrier" -> "https://bowwowinsurance.com.au/wp-content/uploads/2018/10/american-staffordshire-terrier-amstaff-american-staffy-700x700.jpg"
                        "beagle" -> "https://bowwowinsurance.com.au/wp-content/uploads/2018/10/beagle-700x700.jpg"
                        "dálmata" -> "https://bowwowinsurance.com.au/wp-content/uploads/2018/10/dalmatian-700x700.jpg"
                        else -> rutaImagen
                    }

                    Mascota(
                        id = binding.cuadroIdenti.text.toString().toInt(),
                        nombre = nombre,
                        imagen = imagen,
                        raza = raza,
                        sexo = binding.cuadroSexo.text.toString(),
                        fechaNacimiento = it,
                        dni = binding.cuadroDNI.text.toString()
                    )
                }

                if (mascota != null) {
                    databaseMascotas.child(mascota.id.toString()).setValue(mascota)
                        .addOnSuccessListener {
                            val snackbar = Snackbar.make(
                                binding.root,
                                "Operación alta realizada",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        }.addOnFailureListener {
                            val snackbar = Snackbar.make(
                                binding.root,
                                "Error al insertar la mascota",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        }

                }

                binding.cuadroIdenti.text.clear()
                binding.cuadroNombre.text.clear()
                binding.cuadroRaza.text.clear()
                binding.cuadroSexo.text.clear()
                binding.cuadroNac.text.clear()
                binding.cuadroDNI.text.clear()
                binding.imagenGaler.setImageURI(null)
            }
        }

        binding.botonModifica.setOnClickListener {

            // Verifica si alguno de los campos está vacío
            if (binding.cuadroIdenti.text.isEmpty() ||
                binding.cuadroNombre.text.isEmpty() ||
                binding.cuadroRaza.text.isEmpty() ||
                binding.cuadroSexo.text.isEmpty() ||
                binding.cuadroNac.text.isEmpty() ||
                binding.cuadroDNI.text.isEmpty()
            ) {
                // Muestra un Snackbar indicando que se deben completar todos los campos
                Snackbar.make(
                    binding.root,
                    "Por favor, completa todos los campos",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val formato = SimpleDateFormat("dd-MM-yyyy")
                val mascota = formato.parse(binding.cuadroNac.text.toString())?.let {
                    val nombre = binding.cuadroNombre.text.toString()
                    val raza = binding.cuadroRaza.text.toString()
                    val imagen = when (raza.toLowerCase(Locale.ROOT)) {
                        "husky" -> "https://bowwowinsurance.com.au/wp-content/uploads/2020/09/shutterstock_15250126-Alaskan-Husky-in-front-of-white-background-thumbnail.jpg"
                        "staffordshire terrier" -> "https://bowwowinsurance.com.au/wp-content/uploads/2018/10/american-staffordshire-terrier-amstaff-american-staffy-700x700.jpg"
                        "beagle" -> "https://bowwowinsurance.com.au/wp-content/uploads/2018/10/beagle-700x700.jpg"
                        "dálmata" -> "https://bowwowinsurance.com.au/wp-content/uploads/2018/10/dalmatian-700x700.jpg"
                        else -> rutaImagen
                    }

                    Mascota(
                        id = binding.cuadroIdenti.text.toString().toInt(),
                        nombre = nombre,
                        imagen = imagen,
                        raza = raza,
                        sexo = binding.cuadroSexo.text.toString(),
                        fechaNacimiento = it,
                        dni = binding.cuadroDNI.text.toString()
                    )
                }

                if (mascota != null) {
                    databaseMascotas.child(mascota.id.toString()).setValue(mascota)
                        .addOnSuccessListener {
                            val snackbar = Snackbar.make(
                                binding.root,
                                "Operación modificación realizada",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        }.addOnFailureListener {
                            val snackbar = Snackbar.make(
                                binding.root,
                                "Error al modificar la mascota",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.show()
                        }

                }

                binding.cuadroIdenti.text.clear()
                binding.cuadroNombre.text.clear()
                binding.cuadroRaza.text.clear()
                binding.cuadroSexo.text.clear()
                binding.cuadroNac.text.clear()
                binding.cuadroDNI.text.clear()
                binding.imagenGaler.setImageURI(null)
            }
        }
        binding.botonConsulta.setOnClickListener {
            val idText = binding.cuadroIdenti.text.toString()

            if (idText.isNotEmpty()) {
                val id = idText.toInt()

                databaseMascotas.child(id.toString()).get().addOnSuccessListener { dataSnapshot ->
                    val mascota = dataSnapshot.getValue(Mascota::class.java)

                    if (mascota != null) {
                        binding.cuadroNombre.setText(mascota.nombre)
                        binding.cuadroRaza.setText(mascota.raza)
                        binding.cuadroSexo.setText(mascota.sexo)
                        binding.cuadroNac.setText(SimpleDateFormat("dd-MM-yyyy").format(mascota.fechaNacimiento))
                        binding.cuadroDNI.setText(mascota.dni)
                        Snackbar.make(
                            binding.root,
                            "Operación consulta realizada",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        // Mostrar Snackbar indicando que no hay mascota disponible con ese ID
                        Snackbar.make(
                            binding.root,
                            "No hay mascota disponible con ese ID",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        // Limpiar los campos y el ImageView
                        binding.cuadroNombre.text.clear()
                        binding.cuadroRaza.text.clear()
                        binding.cuadroSexo.text.clear()
                        binding.cuadroNac.text.clear()
                        binding.cuadroDNI.text.clear()
                        binding.imagenGaler.setImageURI(null)
                    }
                }.addOnFailureListener {
                    // Mostrar Snackbar indicando que hubo un error al obtener los datos de Firebase
                    Snackbar.make(
                        binding.root,
                        "Error al obtener los datos de Firebase",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } else {
                // Mostrar Snackbar indicando que es necesario ingresar un ID antes de consultar
                Snackbar.make(
                    binding.root,
                    "Por favor, ingresa un ID antes de consultar",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }


        // Botón de borrado
        binding.botonBorrar.setOnClickListener {
            val idText = binding.cuadroIdenti.text.toString()

            // Verifica si el campo de ID está vacío
            if (idText.isEmpty()) {
                // Muestra un Snackbar indicando que es necesario ingresar un ID
                Snackbar.make(
                    binding.root,
                    "Por favor, ingresa un ID antes de borrar",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val id = idText.toInt()

                // Eliminar la mascota de Firebase
                databaseMascotas.child(id.toString()).removeValue().addOnSuccessListener {
                    val snackbar =
                        Snackbar.make(
                            binding.root,
                            "Operación borrado realizada",
                            Snackbar.LENGTH_LONG
                        )
                    snackbar.show()

                    binding.cuadroIdenti.text.clear()
                    binding.cuadroNombre.text.clear()
                    binding.cuadroRaza.text.clear()
                    binding.cuadroSexo.text.clear()
                    binding.cuadroNac.text.clear()
                    binding.cuadroDNI.text.clear()
                }.addOnFailureListener {
                    val snackbar =
                        Snackbar.make(
                            binding.root,
                            "Error al borrar la mascota",
                            Snackbar.LENGTH_SHORT
                        )
                    snackbar.show()
                }
            }
        }
        binding.botonConsultarTodas.setOnClickListener {
            val lista = Intent(this@RegistroAnimales, Lista::class.java)
            lista.putExtra("usuario", username)
            lista.putExtra("tipo", tipo)
            startActivity(lista)
        }
        // Botón administrar Usuarios
        binding.buttonAdministrarUsuarios.setOnClickListener {
            val intent = Intent(this, AdiminUsuarios::class.java)
            intent.putExtra("usuario", username)
            intent.putExtra("tipo", tipo)
            startActivity(intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODIGO_SELECCION_IMAGEN && resultCode == Activity.RESULT_OK && data != null) {
            // Aquí puedes manejar la imagen seleccionada
            val imagenSeleccionada = data.data
            // Convertir la URI en una cadena de ruta de imagen
            val rutaImagen = imagenSeleccionada.toString()
            // Mostrar la imagen seleccionada en el ImageView
            binding.imagenGaler.setImageURI(imagenSeleccionada)

            // Almacena la ruta de la imagen en la base de datos
            binding.botonAlta.setOnClickListener {

                val formato = SimpleDateFormat("dd-MM-yyyy")
                val mascota = formato.parse(binding.cuadroNac.text.toString())?.let {
                    Mascota(
                        id = binding.cuadroIdenti.text.toString().toInt(),
                        nombre = binding.cuadroNombre.text.toString(),
                        imagen = rutaImagen, // Se asigna la ruta de la imagen
                        raza = binding.cuadroRaza.text.toString(),
                        sexo = binding.cuadroSexo.text.toString(),
                        fechaNacimiento = it,
                        dni = binding.cuadroDNI.text.toString()
                    )
                }
                if (mascota != null) {

                    val snackbar =
                        Snackbar.make(binding.root, "Mascota insertada", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }

                binding.cuadroIdenti.text.clear()
                binding.cuadroNombre.text.clear()
                binding.cuadroRaza.text.clear()
                binding.cuadroSexo.text.clear()
                binding.cuadroNac.text.clear()
                binding.cuadroDNI.text.clear()
                binding.imagenGaler.setImageURI(null) // Limpiar el ImageView

            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // No hacemos nada para evitar que el usuario retroceda
    }
}