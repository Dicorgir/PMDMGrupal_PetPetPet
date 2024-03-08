package com.example.petpetpet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.petpetpet.databinding.ActivityVerAnimalBinding
import java.text.SimpleDateFormat
import java.util.Date
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class VerAnimal : AppCompatActivity() {
    private lateinit var binding: ActivityVerAnimalBinding

    companion object {
        private const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerAnimalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cod = intent.getIntExtra("cod", 0)
        val nombre = intent.getStringExtra("nombre")
        val raza = intent.getStringExtra("raza")
        val fechaNacimientoLong = intent.getLongExtra("fecha_nacimiento", 0)
        val fechaNacimiento = Date(fechaNacimientoLong) // Convertir a Date
        val sexo = intent.getStringExtra("sexo")
        val dni = intent.getStringExtra("dni")
        val foto = intent.getStringExtra("foto")

        // Ahora, actualiza la interfaz de usuario con estos valores
        binding.codAnimal.setText(cod.toString())
        binding.nomAnimal.setText(nombre)
        binding.razaAnimal.setText(raza)
        binding.sexoAnimal.setText(sexo)
        binding.dniAnimal.setText(dni)
        binding.fechAnimal.setText(SimpleDateFormat("dd/MM/yyyy").format(fechaNacimiento))

        // Comprueba si tienes el permiso READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            // Si no se concede el permiso, solicítalo
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
        } else {
            // Si se concede el permiso, carga la imagen como antes
            Glide.with(this).load(foto).into(binding.fotoAnimal)
        }
    }
}