package com.example.projectappmovil

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectappmovil.navegation.AppScreens

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Perfil(){
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("",
                    fontSize = 20.sp
                ) },
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.vueloenavion),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                },
                actions = {
                    SmallFloatingActionButton (
                        onClick = { },
                        containerColor = Color.White
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
            )

        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    onClick = {},
                    selected = true,
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                    label = { Text("MENU") }
                )
                NavigationBarItem(
                    onClick = {},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Place, contentDescription = null) },
                    label = { Text("REPORTS") }
                )
                NavigationBarItem(
                    onClick = {},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                    label = { Text("MINE") }
                )
                NavigationBarItem(
                    onClick = {},
                    selected = false,
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
            Text(
                text = "Perfil",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            var nombre by remember { mutableStateOf("") }
            var ciudad by remember { mutableStateOf("") }
            var direccion by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var contrasenia by remember { mutableStateOf("") }
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
                value = email,
                onValueChange = { newText -> email = newText },
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
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text(text = "GUARDAR")
            }

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                    )
                ) {
                    Text(text = "CERRAR SESION")

                }
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                    )
                ) {
                    Text(text = "ELIMINAR CUENTA")

                }

        }
    }
}