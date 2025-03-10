package com.example.projectappmovil.controller

import androidx.navigation.NavController
import com.example.projectappmovil.navegation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginController {
    fun iniciarSesion(email: String, password: String, navController: NavController){
        val auth: FirebaseAuth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Inicio de sesión exitoso")
                    navController.navigate(route = AppScreens.InicioScreen.route)
                    val user = auth.currentUser
                    println("Usuario logueado: ${user?.email}")
                } else {
                    inicioSesionAdmin(email, password, navController)
                }
            }
    }

    fun inicioSesionAdmin(email: String, password: String, navController: NavController){
        val db = Firebase.firestore
        db.collection("administradores")
            .whereEqualTo("email", email)
            .whereEqualTo("contrasenia", password)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    navController.navigate(route = AppScreens.InicioAdminScreen.route)
                } else {
                    println("Inicio de sesión fallido")
                }
            }
    }
}