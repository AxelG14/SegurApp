package com.example.projectappmovil.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectappmovil.Inicio
import com.example.projectappmovil.CreateReport
import com.example.projectappmovil.Reports1
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
            Inicio(navController)
        }
        composable(AppScreens.CreateReportScreen.route) {
            CreateReport()
        }
        composable(AppScreens.ReportScreen.route) {
            Reports1()
        }


        }
}