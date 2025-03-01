package com.example.projectappmovil.Controller

import androidx.navigation.NavController
import com.example.projectappmovil.Navegation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginController() {
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

                    try {
                        throw task.exception ?: Exception("Error desconocido")
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        println("Error: Credenciales inválidas (correo o contraseña incorrectos).")
                    } catch (e: FirebaseAuthInvalidUserException) {
                        println("Error: El usuario no existe.")
                    } catch (e: Exception) {
                        println("Error: ${e.message}")
                    }
                }
            }
    }
}