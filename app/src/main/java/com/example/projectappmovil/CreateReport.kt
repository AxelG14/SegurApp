package com.example.projectappmovil
import androidx.activity.compose.rememberLauncherForActivityResult
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.projectappmovil.Controller.CreateReportController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

@Preview
@Composable
fun createReport(){
    Scaffold (
        bottomBar = {
            NavigationBar { NavigationBarItem(
                onClick = {},
                selected = true,
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                label = { Text("INICIO") }
            )
                NavigationBarItem(
                    onClick = {},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Place, contentDescription = null) },
                    label = { Text("REPORTES") }
                )
                NavigationBarItem(
                    onClick = {},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                    label = { Text("MI REPORT") }
                )
                NavigationBarItem(
                    onClick = {},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                    label = { Text("MI PERFIL") }
                )
            }
        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly

        ) {
            Text(
                text = "CREAR REPORTE",
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            var titulo by remember { mutableStateOf("") }
            var categoria by remember { mutableStateOf("") }
            var descripcion by remember { mutableStateOf("") }
            var ubicacion by remember { mutableStateOf("") }
            TextField(
                value = titulo,
                onValueChange = { newTitulo ->
                    titulo = newTitulo },
                modifier = Modifier.fillMaxWidth(0.9f),
                label = { Text("Titulo") }
            )
            TextField(
                value = categoria,
                onValueChange = { newCategoria ->
                    categoria = newCategoria },
                modifier = Modifier.fillMaxWidth(0.9f),
                label = { Text("Categoria") }
            )
            TextField(
                value = descripcion,
                onValueChange = { newDescripcion ->
                    descripcion = newDescripcion },
                modifier = Modifier.fillMaxWidth(0.9f),
                label = { Text("Descripcion") }
            )

            TextField(
                value = ubicacion,
                onValueChange = { newUbicacion ->
                    ubicacion = newUbicacion },
                modifier = Modifier.fillMaxWidth(0.9f),
                label = { Text("ubicacion") }
            )

            Spacer(modifier = Modifier.height(30.dp))

            var imageUri by remember { mutableStateOf<Uri?>(null) }
            val context = LocalContext.current

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = { uri: Uri? ->
                    imageUri = uri
                }
            )
            if (imageUri != null) {

                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            } else {
                Text("No image selected")
            }

            Button(
                onClick = { launcher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Select Image from Gallery")
            }

            Spacer(modifier = Modifier.height(10.dp))

            val createReportController = CreateReportController()
            Button(
                onClick = {
                    imageUri?.let { uri ->
                        createReportController.saveReportImageToFirebaseStorage(
                            titulo,
                            categoria,
                            descripcion,
                            ubicacion,
                            uri
                        )
                    }
                },
                    modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(0.9f)
            ) {
                Text(text = "CREAR REPORTE")
            }
        }
    }
}



