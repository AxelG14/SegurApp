package com.example.projectappmovil.controller

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileController {
    fun udpateInfo(email: String, nombre: String, ciudad: String, direccion: String, correo: String, contrasenia: String){
        val db = Firebase.firestore
        val auth = Firebase.auth
        val user = auth.currentUser

        val data = mapOf(
            "nombre" to nombre,
            "ciudad" to ciudad,
            "direccion" to direccion,
            "email" to correo,
            "contrasenia" to contrasenia
        )
        db.collection("clientes")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val documentId = querySnapshot.documents[0].id
                    db.collection("clientes")
                        .document(documentId)
                        .update(data)
                        .addOnSuccessListener {
                            if (email != user?.email){
                                user?.verifyBeforeUpdateEmail(email)
                                    ?.addOnSuccessListener {
                                        println("Email verificado")
                                    }

                            }
                            if (contrasenia.isNotEmpty()){
                                user?.updatePassword(contrasenia)
                                    ?.addOnSuccessListener {
                                        println("Contraseña actualizada")
                                    }
                            }
                        }
                }
            }
    }
    fun signOut(){
        val auth = Firebase.auth
        auth.signOut()
    }
}