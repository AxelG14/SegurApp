package com.example.projectappmovil.controller

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class RegisterController {

    fun agregarClienteFirestore(
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
    fun agregarClienteAuth(email: String, password: String){
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Registro exitoso")
                } else {
//
                }
        }
    }
}