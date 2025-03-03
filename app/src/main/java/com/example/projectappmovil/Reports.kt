
package com.example.projectappmovil

import androidx.compose.runtime.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Reports1() {
    val auth: FirebaseAuth = Firebase.auth
    val email = auth.currentUser?.email
    val userId = auth.currentUser?.uid
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    onClick = {},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                    label = { Text("MENU") }
                )
                NavigationBarItem(
                    onClick = {},
                    selected = true,
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
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null)},
                    label = { Text("NOTIF") }
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("$email",
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
                            modifier = Modifier.size(40.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
            )
        }
    ) { innerPadding ->
        LoadImageFromFirestore2(userId!!, innerPadding)
    }
}

@Composable
fun LoadImageFromFirestore2(userId: String, innerPadding: PaddingValues) {
    val db = Firebase.firestore
    var reports by remember { mutableStateOf<List<Report>>(emptyList()) }

    // Escuchar cambios en Firestore
    LaunchedEffect(userId) {
        val listenerRegistration = db.collection("clientes")
            .document(userId)
            .collection("reportes")
            .addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
                if (exception != null) {
                    println("Error listening to Firestore: ${exception.message}")
                    return@addSnapshotListener
                }
                // Convertir los documentos en una lista de Report
                val newReports = snapshot?.documents?.mapNotNull { document ->
                    Report(
                        imageUrl = document.getString("imageUrl"),
                        title = document.getString("titulo") ?: "",
                        categoria = document.getString("categoria") ?: "",
                        description = document.getString("descripcion") ?: "",
                        ubication = document.getString("ubicacion") ?: ""
                    )
                } ?: emptyList()

                reports = newReports
            }
//        onDispose {
//            listenerRegistration.remove()
//        }
    }

    MyLazyColumn(reports = reports, innerPadding)
}

data class Report(
    val imageUrl: String?,
    val title: String,
    val categoria: String,
    val description: String,
    val ubication: String
)

@Composable
fun MyLazyColumn(reports: List<Report>, innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        items(reports) { report ->
            Card(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxSize()

            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(report.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Text(text = "Titulo: " + report.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp)
                    Text(text = "Categoria: " + report.categoria,
                        fontSize = 12.sp)
                    Text(text = "Descripcion: "+report.description,
                        fontSize = 12.sp)
                    Text(text = "Ubicacion: "+report.ubication,
                        fontSize = 12.sp)
                    Row (
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.End

                    ) {
                        Button(
                            onClick = {},

                        ) {
                            Text(text = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}





