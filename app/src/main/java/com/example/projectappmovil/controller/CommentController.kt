package com.example.projectappmovil.controller

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CommentController {
    val db = Firebase.firestore

    fun saveComment(
        nombre: String, descripcion: String, userId: String
    ) {
        val comentario = hashMapOf(
            "nombre" to nombre,
            "descripcion" to descripcion,
            "userId" to userId
        )
        db.collection("comentarios")
            .add(comentario)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot written with ID: ${documentReference.id}")
            }
    }
}