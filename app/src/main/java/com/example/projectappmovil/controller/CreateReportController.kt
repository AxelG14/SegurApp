package com.example.projectappmovil.controller

import android.net.Uri
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class CreateReportController {

    fun saveReport(userId: String, titulo: String, categoria: String, descripcion: String,
                   ubicacion: String, imageUrl: String){
        val db = Firebase.firestore
        val report = hashMapOf(
            "titulo" to titulo,
            "categoria" to categoria,
            "descripcion" to descripcion,
            "ubicacion" to ubicacion,
            "imageUrl" to imageUrl
        )
        db.collection("clientes")
            .document(userId)
            .collection("reportes")
            .add(report)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot written with ID: ${documentReference.id}")
            }
    }

    fun saveReportImageToFirebaseStorage(
        userId: String,
        titulo: String,
        categoria: String,
        descripcion: String,
        ubicacion: String,
        imageUri: Uri
    ) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imageRef = storageRef.child("/users/$userId/images/${UUID.randomUUID()}.jpg")

        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener {
            // La imagen se subió correctamente
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                saveReport(userId, titulo, categoria, descripcion, ubicacion ,imageUrl)
            }
        }.addOnFailureListener { exception ->
            println("Error uploading image: ${exception.message}")
        }
    }
}