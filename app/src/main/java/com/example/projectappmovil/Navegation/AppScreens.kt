package com.example.projectappmovil.Navegation

open class AppScreens(val route: String) {
    object MainScreen : AppScreens("main_screen")
    object RegisterScreen : AppScreens("register_screen")
    object ForgotScreen : AppScreens("forgot_screen")
    object InicioScreen : AppScreens("Inicio_screen")
}