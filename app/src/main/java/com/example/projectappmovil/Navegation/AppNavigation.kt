package com.example.projectappmovil.Navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectappmovil.MainActivity
import com.example.projectappmovil.createReport
import com.example.projectappmovil.inicio2
import com.example.projectappmovil.password
import com.example.projectappmovil.previewLogin
import com.example.projectappmovil.registro

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(AppScreens.MainScreen.route) {
            previewLogin(navController)
        }
        composable(AppScreens.RegisterScreen.route) {
            registro(navController)
        }
        composable(AppScreens.ForgotScreen.route) {
            password(navController)
        }
        composable(AppScreens.InicioScreen.route) {
            inicio2(navController)
        }
        composable(AppScreens.CreateReportScreen.route) {
            createReport()
        }


        }
}