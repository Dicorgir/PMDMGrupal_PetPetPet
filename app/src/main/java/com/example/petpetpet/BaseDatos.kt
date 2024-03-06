package com.example.petpetpet


// Manejo datos para firebase
class BaseDatoss {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "PetPetPetBD"

        // Mascota table
        private const val TABLE_MASCOTA = "mascota"
        private const val COLUMN_ID = "id_mascota"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_IMAGEN = "imagen"
        private const val COLUMN_RAZA = "raza"
        private const val COLUMN_SEXO = "sexo"
        private const val COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento"
        private const val COLUMN_DNI = "dni"

        // Usuario table
        const val TABLE_USUARIO = "usuario"
        const val COLUMN_NAME = "nombre"
        const val COLUMN_USERNAME = "contenido_usuario"
        const val COLUMN_PASSWORD = "contrasena"
    }

    override fun insertarUsuario(nombreUsuario: String, nombreCompleto: String, contrasena: String, tipo: String): Boolean {
        
    }
}