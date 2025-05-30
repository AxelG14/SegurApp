package com.example.projectappmovil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException


@Composable
fun Password(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¿OLVIDASTE TU CONTRASEÑA?",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 25.dp),
            fontWeight = FontWeight.Bold
        )

        var email by remember { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = { newEmail -> email = newEmail },
            label = { Text("EMAIL") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(0.9f),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            }
        )

        var showErrorDialog by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf("") }

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("ERROR") },
                text = { Text(errorMessage.ifEmpty { "Debes de llenar todos los campos" }) },
                confirmButton = {
                    Button(onClick = { showErrorDialog = false }) {
                        Text("Aceptar")
                    }
                }
            )
        }

        var showSuccessDialog by remember { mutableStateOf(false) }
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = { Text("ÉXITO") },
                text = { Text("Se ha enviado un correo para restablecer tu contraseña") },
                confirmButton = {
                    Button(onClick = {
                        showSuccessDialog = false
                        navController.popBackStack()
                    }) {
                        Text("Aceptar")
                    }
                }
            )
        }

        Button(
            onClick = {
                if (email.isBlank()) {
                    errorMessage = "Debes ingresar un correo electrónico"
                    showErrorDialog = true
                } else {
                    sendPasswordResetEmail(
                        email = email,
                        onSuccess = { showSuccessDialog = true },
                        onError = { exception ->
                            errorMessage = when (exception) {
                                is FirebaseAuthInvalidUserException -> "No existe una cuenta con este correo"
                                else -> "Error al enviar el correo: ${exception.message}"
                            }
                            showErrorDialog = true
                        }
                    )
                }
            },
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(text = "CONFIRMAR")
        }

        // Botón INICIAR SESIÓN (fuera del AlertDialog)
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(text = "INICIAR SESIÓN")
        }
    }
}

// Función para enviar correo de recuperación (sin cambios)
fun sendPasswordResetEmail(
    email: String,
    onSuccess: () -> Unit,
    onError: (Exception) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    auth.sendPasswordResetEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception ?: Exception("Error desconocido"))
            }
        }
}
