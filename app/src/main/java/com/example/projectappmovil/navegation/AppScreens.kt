package com.example.projectappmovil.navegation

open class AppScreens(val route: String) {
    object MainScreen : AppScreens("main_screen")
    object RegisterScreen : AppScreens("register_screen")
    object ForgotScreen : AppScreens("forgot_screen")
    object InicioScreen : AppScreens("inicio_screen")
    object CreateReportScreen : AppScreens("createReport_screen")
    object ReportScreen : AppScreens("report_screen")
    object ProfileScreen : AppScreens("profile_screen")
}