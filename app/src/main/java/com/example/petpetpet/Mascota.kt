package com.example.petpetpet

import java.util.*

class Mascota() {
    var id: Int = 0
    var nombre: String = ""
    var imagen: String = ""
    var raza: String = ""
    var sexo: String = ""
    var fechaNacimiento: Date = Date()
    var dni: String = ""

    constructor(
        id: Int,
        nombre: String,
        imagen: String,
        raza: String,
        sexo: String,
        fechaNacimiento: Date,
        dni: String
    ) : this() {
        this.id = id
        this.nombre = nombre
        this.imagen = imagen
        this.raza = raza
        this.sexo = sexo
        this.fechaNacimiento = fechaNacimiento
        this.dni = dni
    }
}