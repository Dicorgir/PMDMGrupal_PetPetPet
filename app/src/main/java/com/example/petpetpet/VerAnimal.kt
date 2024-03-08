package com.example.petpetpet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.petpetpet.databinding.ActivityVerAnimalBinding
import java.text.SimpleDateFormat
import java.util.Date

class VerAnimal : AppCompatActivity() {
    private lateinit var binding: ActivityVerAnimalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_animal)

        val cod = intent.getIntExtra("cod", 0)
        val nombre = intent.getStringExtra("nombre")
        val raza = intent.getStringExtra("raza")
        val fechaNacimientoLong = intent.getLongExtra("fecha_nacimiento", 0)
        val fechaNacimiento = Date(fechaNacimientoLong) // Convertir a Date
        val sexo = intent.getStringExtra("sexo")
        val dni = intent.getStringExtra("dni")
        val foto = intent.getStringExtra("foto")

        // Ahora, actualiza la interfaz de usuario con estos valores
        findViewById<TextView>(R.id.cod_animal).text = cod.toString()
        findViewById<TextView>(R.id.nom_animal).text = nombre
        findViewById<TextView>(R.id.raza_animal).text = raza
        findViewById<TextView>(R.id.sexo_animal).text = sexo
        findViewById<TextView>(R.id.dni_animal).text = dni
        findViewById<TextView>(R.id.fech_animal).text = SimpleDateFormat("dd/MM/yyyy").format(fechaNacimiento)
        Glide.with(this).load(foto).into(findViewById<ImageView>(R.id.fotoAnimal))
    }
}