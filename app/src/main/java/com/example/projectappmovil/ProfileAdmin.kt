package com.example.projectappmovil

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectappmovil.controller.CreateReportController
import com.example.projectappmovil.controller.ProfileController
import com.example.projectappmovil.navegation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileAdmin(navController: NavController){
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val email = user?.email ?: ""

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = "Perfil",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ) },
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.size(70.dp)
                    )
                },
                actions = {
                    SmallFloatingActionButton (
                        onClick = { navController.navigate(route = AppScreens.NotificationScreen.route)
                            CreateReportController.GlobalData.notification.value = 0 },
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Badges(CreateReportController.GlobalData.notification.value)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.InicioAdminScreen.route)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                    label = { Text("MENU") }
                )
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.ReportsAdminScreen.route)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                    label = { Text("REPORTS") }
                )
                NavigationBarItem(
                    onClick = {},
                    selected = true,
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                    label = { Text("PROFILE") }
                )
            }

        }

    ) { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            val db = Firebase.firestore
            var nombre by remember { mutableStateOf("") }
            var ciudad by remember { mutableStateOf("") }
            var direccion by remember { mutableStateOf("") }
            var correo by remember { mutableStateOf("") }
            var contrasenia by remember { mutableStateOf("") }

            LaunchedEffect(email) {
                db.collection("usuarios")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty){
                            val document = querySnapshot.documents[0]
                            nombre = document.getString("nombre") ?: ""
                            ciudad = document.getString("ciudad") ?: ""
                            direccion = document.getString("direccion") ?: ""
                            correo = document.getString("email") ?: ""
                            contrasenia = document.getString("contrasenia") ?: ""
                        }
                    }
            }
            TextField(
                value = nombre,
                onValueChange = { newText -> nombre = newText },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(0.8f),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                }
            )
            TextField(
                value = ciudad,
                onValueChange = { newText -> ciudad = newText },
                label = { Text("Ciudad") },
                modifier = Modifier.fillMaxWidth(0.8f),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Home, contentDescription = null)
                }
            )
            TextField(
                value = direccion,
                onValueChange = { newText -> direccion = newText },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth(0.8f),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Place, contentDescription = null)
                }
            )
            TextField(
                value = correo,
                onValueChange = { newText -> correo = newText },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(0.8f),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                }
            )
            TextField(
                value = contrasenia,
                onValueChange = { newText -> contrasenia = newText },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(0.8f),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Create, contentDescription = null)
                }
            )

            val profile = ProfileController()
            var showDialog by remember { mutableStateOf(false) }
            var showDialog2 by remember { mutableStateOf(false) }
            Button(
                onClick = {
                    if (nombre.isEmpty() || ciudad.isEmpty() || direccion.isEmpty()
                        || correo.isEmpty() || contrasenia.isEmpty()) {
                        showDialog = true
                    } else {
                        profile.udpateInfo(email, nombre, ciudad, direccion, correo, contrasenia)
                        showDialog2 = true
                    } },
                modifier = Modifier
                    .height(45.dp)
                    .fillMaxWidth(0.7f)
            ) {
                Text(text = "GUARDAR CAMBIOS")
            }
            Button(
                onClick = {
                    profile.signOut()
                    navController.navigate(route = AppScreens.MainScreen.route)
                          },
                modifier = Modifier.fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                )
            ) {
                Text(text = "CERRAR SESION")
            }
            val delete = ProfileController()
            Button(
                onClick = { delete.deleteProfile(user!!.uid) },
                modifier = Modifier.fillMaxWidth(0.6f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                )
            ) {
                Text(text = "ELIMINAR CUENTA")

            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Error") },
                    text = { Text("Debe llenar todos los campos!") },
                    confirmButton = {
                        Button(
                            onClick = { showDialog = false }
                        ) {
                            Text("Aceptar")
                        }
                    }
                )
            }
            if (showDialog2) {
                AlertDialog(
                    onDismissRequest = { showDialog2 = false },
                    title = { Text("Éxito") },
                    text = { Text("Se han guardado los cambios!") },
                    icon = {Icons.Default.Check},
                    confirmButton = {
                        Button(
                            onClick = { showDialog2 = false }
                        ) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
    }
}


