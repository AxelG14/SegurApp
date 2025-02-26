package com.example.projectappmovil

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.projectappmovil.Navegation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


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
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    showErrorDialog = true
                } else {
                    findUserByEmailAndPassword(
                        collectionName = "clientes",
                        email = email,
                        password = password
                    ) { userFound ->
                        if (userFound) {
                            navController.navigate(route = AppScreens.InicioScreen.route)
                        } else {
                            showErrorDialog2 = true
                        }
                    }
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
                onDismissRequest = { showErrorDialog = false }, // Cerrar el diálogo
                title = { Text("ERROR") },
                text = { Text("DEBES DE LLENAR TODOS LOS CAMPOS") },
                confirmButton = {
                    Button(
                        onClick = { showErrorDialog = false } // Cerrar el diálogo
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }

        if (showErrorDialog2) {
            AlertDialog(
                onDismissRequest = { showErrorDialog2 = false }, // Cerrar el diálogo
                title = { Text("ERROR") },
                text = { Text("NO SE ENCONTRO EL USUARIO") },
                confirmButton = {
                    Button(
                        onClick = { showErrorDialog2 = false } // Cerrar el diálogo
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

@Composable
fun previewLogin(navController: NavController){
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

fun findUserByEmailAndPassword(
    collectionName: String,
    email: String,
    password: String,
    onResult: (Boolean) -> Unit
) {
    val db = Firebase.firestore

    db.collection(collectionName)
        .whereEqualTo("email", email) // Busca el campo "email"
        .whereEqualTo("contrasenia", password) // Busca el campo "password"
        .limit(1) // Limita la búsqueda a un solo documento
        .get()
        .addOnSuccessListener { querySnapshot ->
            // Si se encuentra un documento, devuelve `true`
            onResult(!querySnapshot.isEmpty)
        }
        .addOnFailureListener { exception ->
            println("Error buscando usuario: ${exception.message}")
            onResult(false) // Devuelve `false` en caso de error
        }
}



