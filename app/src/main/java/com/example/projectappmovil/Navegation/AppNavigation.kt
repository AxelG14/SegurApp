package com.example.projectappmovil.Navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectappmovil.MainActivity
import com.example.projectappmovil.previewLogin
import com.example.projectappmovil.registro
import com.example.projectappmovil.registro2

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(AppScreens.MainScreen.route) {
            previewLogin(navController)
        }
        composable(AppScreens.RegisterScreen.route) {
            registro2(navController)
        }

        }
}