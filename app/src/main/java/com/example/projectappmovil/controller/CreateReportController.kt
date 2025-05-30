package com.example.projectappmovil.controller

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import com.mapbox.geojson.Point


class CreateReportController {
    val db = Firebase.firestore
    fun saveReport(
        userId: String, titulo: String, categoria: String, descripcion: String, imageUrl: String, name: String, latitude: String,
        longitude: String
    ) {
        val report = hashMapOf(
            "titulo" to titulo,
            "categoria" to categoria,
            "descripcion" to descripcion,
            "imageUrl" to imageUrl,
            "nombre" to name,
            "userId" to userId,
            "check" to false,
            "countMessages" to 0,
            "latitude" to latitude,
            "longitude" to longitude

        )
        db.collection("reportes")
            .add(report)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot written with ID: ${documentReference.id}")
                val id = documentReference.id
                db.collection("reportes")
                    .document(id)
                    .update("idReport", id)
                GlobalData.notification.value += 1

            }
    }

    object GlobalData {
        val notification =  mutableStateOf(0)
    }

    fun saveReportImageToFirebaseStorage(
        userId: String,
        titulo: String,
        categoria: String,
        descripcion: String,
        imageUri: String,
        nombre: String,
        latitude: String,
        longitude: String
    ) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imageRef = storageRef.child("/$userId/images/${UUID.randomUUID()}.jpg")

        val uri = Uri.parse(imageUri)
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                saveReport(userId, titulo, categoria, descripcion, imageUrl, nombre, latitude, longitude)
            }
        }.addOnFailureListener { exception ->
            println("Error uploading image: ${exception.message}")
        }
    }

    fun updateCountMessage(reportId: String){
        db.collection("reportes")
            .document(reportId)
            .update("countMessages", FieldValue.increment(1))
            .addOnSuccessListener { println("countMessages updated successfully") }
    }

    fun fetchAllReportPoints(onComplete: (List<Point>) -> Unit) {
        db.collection("reportes")
            .get()
            .addOnSuccessListener { snapshot ->
                val points = snapshot.documents.mapNotNull { doc ->
                    val lat = doc.getDouble("latitude")
                    val lon = doc.getDouble("longitude")
                    if (lat != null && lon != null) Point.fromLngLat(lon, lat) else null
                }
                onComplete(points)
            }
            .addOnFailureListener {
                onComplete(emptyList())
            }
    }



}