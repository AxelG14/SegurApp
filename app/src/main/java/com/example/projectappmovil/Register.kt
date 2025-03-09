package com.example.projectappmovil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectappmovil.controller.RegisterController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

//Agregar Administradores

fun agregarAdministrador(){
    val db = Firebase.firestore
    val admin2 = hashMapOf(
        "nombre" to "Luis",
        "ciudad" to "Pereira",
        "direccion" to "Calle 456",
        "email" to "luis456@eam.com",
        "contrasenia" to "456"
    )
    db.collection("administradores")
        .add(admin2)
        .addOnSuccessListener { documentReference ->
            println("DocumentSnapshot written with ID: ${documentReference.id}")
        }
        .addOnFailureListener{ e ->
            println("Error adding document $e")
        }
}

@Composable
fun registro(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) {
            Snackbar(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxWidth(0.8f),
                containerColor = MaterialTheme.colorScheme.secondary,
                content = {
                    Text(
                        text = "SE HA REGISTRADO CORRECTAMENTE",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        } },
        content = { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                val screenWidth = maxWidth
                val screenHeight = maxHeight

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "REGISTRO USUARIO",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = if (screenWidth < 400.dp) 20.sp else 26.sp, // Tamaño de fuente responsive
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = if (screenHeight < 600.dp) 20.dp else 50.dp) // Padding responsive
                    )

                    TextField(
                        value = nombre,
                        onValueChange = { newNombre -> nombre = newNombre },
                        label = { Text("NOMBRE") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person, contentDescription = null
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = ciudad,
                        onValueChange = { ciudad = it },
                        label = { Text("CIUDAD") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Place, contentDescription = null
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = direccion,
                        onValueChange = { newDireccion -> direccion = newDireccion },
                        label = { Text("DIRECCION") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Home, contentDescription = null
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = email,
                        onValueChange = { newEmail -> email = newEmail },
                        label = { Text("EMAIL") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email, contentDescription = null
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = contrasenia,
                        onValueChange = { newContrasenia -> contrasenia = newContrasenia },
                        label = { Text("CONTRASEÑA") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit, contentDescription = null
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    val registerController1 = RegisterController()
                    var showErrorDialog by remember { mutableStateOf(false) }
                    Button(
                        onClick = { if (nombre.isBlank() || ciudad.isBlank() || direccion.isBlank()
                            || email.isBlank() || contrasenia.isBlank()){
                            showErrorDialog = true
                        }
                            registerController1.agregarClienteAuth(email, contrasenia)
                            registerController1.agregarClienteFirestore(nombre, ciudad, direccion, email, contrasenia)
                            nombre = "" ; ciudad = "" ; direccion = "" ; email = "" ; contrasenia = ""
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Registro exitoso")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text("REGISTRARSE")
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = {navController.popBackStack()},
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("INICIAR SESION")
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
                }
            }
        }
    )
}



