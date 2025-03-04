package com.example.projectappmovil.controller

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class CreateReportController {
    val db = Firebase.firestore
    fun saveReport(
        userId: String, titulo: String, categoria: String, descripcion: String,
        ubicacion: String, imageUrl: String, name: String
    ) {

        val report = hashMapOf(
            "titulo" to titulo,
            "categoria" to categoria,
            "descripcion" to descripcion,
            "ubicacion" to ubicacion,
            "imageUrl" to imageUrl,
            "nombre" to name
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
        imageUri: Uri,
        nombre: String
    ) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imageRef = storageRef.child("/users/$userId/images/${UUID.randomUUID()}.jpg")

        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                saveReport(userId, titulo, categoria, descripcion, ubicacion, imageUrl, nombre)
            }
        }.addOnFailureListener { exception ->
            println("Error uploading image: ${exception.message}")
        }
    }

}