package com.example.projectappmovil

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectappmovil.controller.LoginController
import com.example.projectappmovil.navegation.AppScreens


@Composable
fun imageLogin(){
    Image(
        painter = painterResource(R.drawable.vueloenavion),
        contentDescription = "Imagen Inicio",
        modifier = Modifier.size(200.dp),
    )
}

@Composable
fun titulo(){
    Text(
        text = "Bienvenidos Apps Moviles",
        color = MaterialTheme.colorScheme.primary,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun body(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = {newEmail ->
                email = newEmail},
            label = { Text("EMAIL") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        var password by remember { mutableStateOf("") }
        TextField(
            value = password,
            onValueChange = {newPass -> password = newPass},
            label = { Text("PASSWORD") }
        )
        Text(
            text = "¿Olvidaste tu contraseña?",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable() {navController.navigate(route = AppScreens.ForgotScreen.route)
            }
        )

        var showErrorDialog by remember { mutableStateOf(false) }
        var showErrorDialog2 by remember { mutableStateOf(false) }
        val loginController = LoginController()
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    showErrorDialog = true
                } else {
                    loginController.iniciarSesion(email, password, navController)

                }
            },
            modifier = Modifier
                .padding(top = 30.dp)
                .size(height = 50.dp, width = 250.dp)
        ) {
            Text("INICIAR SESION")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {navController.navigate(route = AppScreens.RegisterScreen.route)},
            modifier = Modifier.size(height = 50.dp, width = 250.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("REGISTRARSE")
        }
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("ERROR") },
                text = { Text("Debes de llenar todos los campos") },
                confirmButton = {
                    Button(
                        onClick = { showErrorDialog = false }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }

        if (showErrorDialog2) {
            AlertDialog(
                onDismissRequest = { showErrorDialog2 = false },
                title = { Text("ERROR") },
                text = { Text("NO SE ENCONTRO EL USUARIO") },
                confirmButton = {
                    Button(
                        onClick = { showErrorDialog2 = false }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

@Composable
fun PreviewLogin(navController: NavController){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        imageLogin()
        titulo()
        Spacer(modifier = Modifier.height(60.dp))
        body(navController)
    }
}





