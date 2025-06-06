package com.example.projectappmovil.controller

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileController {
    val db = Firebase.firestore
    val auth = Firebase.auth

    fun udpateInfo(email: String, nombre: String, ciudad: String, direccion: String, correo: String, contrasenia: String){
        val user = auth.currentUser

        val data = mapOf(
            "nombre" to nombre,
            "ciudad" to ciudad,
            "direccion" to direccion,
            "email" to correo,
            "contrasenia" to contrasenia
        )
        db.collection("usuarios")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val documentId = querySnapshot.documents[0].id
                    db.collection("usuarios")
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
        auth.signOut()
    }

    fun saveEmergencyUser(context: Context, key: String, value: String) {
        val prefs = context.getSharedPreferences("emergency", Context.MODE_PRIVATE)
        prefs.edit().putString(key, value).apply()
    }

    fun getEmergencyUser(context: Context, key: String): String? {
        val prefs = context.getSharedPreferences("emergency", Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

    fun saveEmergencyUserName(context: Context, key: String, value: String) {
        val prefs = context.getSharedPreferences("emergencyName", Context.MODE_PRIVATE)
        prefs.edit().putString(key, value).apply()
    }

    fun getEmergencyUserName(context: Context, key: String): String? {
        val prefs = context.getSharedPreferences("emergencyName", Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

    fun deleteProfile(userId: String){
        val user: FirebaseUser? = auth.currentUser
        if (user != null) {
            user.delete()
                .addOnSuccessListener {
                        db.collection("usuarios")
                            .document(userId)
                            .delete()
                        println("Usuario eliminado exitosamente")

                }
                    println("Usuario eliminado exitosamente")
                }
    }
}

