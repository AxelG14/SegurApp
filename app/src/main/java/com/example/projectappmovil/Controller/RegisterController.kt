package com.example.projectappmovil.Controller

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class RegisterController {
    fun agregarCliente(
        nombre: String,
        ciudad: String,
        direccion: String,
        email: String,
        contrasenia: String
    ) {
        val db = Firebase.firestore
        val cliente = hashMapOf(
            "nombre" to nombre,
            "ciudad" to ciudad,
            "direccion" to direccion,
            "email" to email,
            "contrasenia" to contrasenia
        )
        db.collection("clientes")
            .add(cliente)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot written with ID: ${documentReference.id}")
            }
    }
}