package com.example.projectappmovil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.projectappmovil.controller.CommentController
import com.example.projectappmovil.controller.CreateReportController
import com.example.projectappmovil.controller.MyReportsController
import com.example.projectappmovil.navegation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.compose.style.MapStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Reports1(navController: NavController) {
    val auth: FirebaseAuth = Firebase.auth
    val userId = auth.currentUser?.uid

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    onClick = { navController.navigate(route = AppScreens.InicioScreen.route) },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                    label = { Text("MENU") }
                )
                NavigationBarItem(
                    onClick = { navController.navigate(route = AppScreens.AllReportsScreen.route) },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Place, contentDescription = null) },
                    label = { Text("REPORTES") }
                )
                NavigationBarItem(
                    onClick = { navController.navigate(route = AppScreens.MyReportsScreen.route) },
                    selected = true,
                    icon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                    label = { Text("PROPIOS", textAlign = TextAlign.Center) },
                )
                NavigationBarItem(
                    onClick = { navController.navigate(route = AppScreens.ProfileScreen.route) },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                    label = { Text("PERFIL") }
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Mis Reportes",
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.logo2),
                        contentDescription = null,
                        modifier = Modifier.size(70.dp)
                    )
                },
                actions = {
                    SmallFloatingActionButton(
                        onClick = {
                            navController.navigate(route = AppScreens.NotificationScreen.route)
                            CreateReportController.GlobalData.notification.value = 0
                        },
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
        }
    ) { innerPadding ->
        LoadImageFromFirestore2(userId!!, innerPadding, navController)
    }
}

@Composable
fun LoadImageFromFirestore2(
    userId: String,
    innerPadding: PaddingValues,
    navController: NavController
) {
    val db = Firebase.firestore
    var reports by remember { mutableStateOf<List<Report>>(emptyList()) }

    LaunchedEffect(userId) {
        db.collection("reportes")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
                if (exception != null) {
                    println("Error listening to Firestore: ${exception.message}")
                    return@addSnapshotListener
                }
                val newReports = snapshot?.documents?.mapNotNull { document ->
                    Report(
                        imageUrl = document.getString("imageUrl"),
                        title = document.getString("titulo") ?: "",
                        categoria = document.getString("categoria") ?: "",
                        description = document.getString("descripcion") ?: "",
                        latitude = document.getString("latitude") ?: "",
                        longitude = document.getString("longitude") ?: "",
                        nombre = document.getString("nombre") ?: "",
                        idReport = document.getString("idReport") ?: ""
                    )
                } ?: emptyList()

                reports = newReports
            }
    }
    MyLazyColumn(reports = reports, innerPadding, navController)
}

data class Report(
    val imageUrl: String?,
    val title: String,
    val categoria: String,
    val description: String,
    val latitude: String,
    val longitude: String,
    val nombre: String,
    val idReport: String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLazyColumn(reports: List<Report>, innerPadding: PaddingValues, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    val myReport = MyReportsController()
    var showDialog2 by remember { mutableStateOf(false) }

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
                    .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(Color.Gray)

            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.primary)
                            .padding(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = Color.Black,
                        )
                        Text(
                            text = "By: ${report.nombre}",
                            fontSize = 15.sp,
                            modifier = Modifier.padding(horizontal = 5.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Image(
                        painter = rememberAsyncImagePainter(report.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                }
                Column(
                    modifier = Modifier
                        .background(color = Color.DarkGray),
                ) {
                    var isEditing by remember { mutableStateOf(true) }
                    var title by remember { mutableStateOf(report.title) }
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        readOnly = isEditing,
                        label = { Text("Titulo") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    )

                    val categorias =
                        listOf("Seguridad", "Infraestructura", "Mascotas", "Comunidad")
                    var categoria by remember { mutableStateOf(report.categoria) }
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            if (!isEditing) {
                                expanded = !expanded
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(vertical = 8.dp)
                    ) {
                        TextField(
                            value = categoria,
                            onValueChange = { categoria = it },
                            label = { Text("Categoria") },
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            textStyle = TextStyle(fontSize = 12.sp)
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categorias.forEach { categoriaItem ->
                                DropdownMenuItem(
                                    text = { Text(categoriaItem) },
                                    onClick = {
                                        categoria = categoriaItem
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    var description by remember { mutableStateOf(report.description) }
                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descripcion") },
                        readOnly = isEditing,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 12.sp)
                    )

                    var latitude by remember { mutableStateOf(report.latitude) }
                    var longitude by remember { mutableStateOf(report.longitude) }
                    var pointClicked by remember { mutableStateOf<Point?>(null) }
                    var markerResourceId by remember { mutableStateOf(R.drawable.red_marker) }

                    val initialPoint = remember(latitude, longitude) {
                        if (latitude.isNotEmpty() && longitude.isNotEmpty()) {
                            Point.fromLngLat(longitude.toDouble(), latitude.toDouble())
                        } else {
                            null
                        }
                    }

                    LaunchedEffect(latitude, longitude) {
                        if (latitude.isNotEmpty() && longitude.isNotEmpty()) {
                            pointClicked =
                                Point.fromLngLat(longitude.toDouble(), latitude.toDouble())
                        }
                    }

                    val marker = rememberIconImage(
                        key = markerResourceId,
                        painter = painterResource(markerResourceId)
                    )

                    MapboxMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        mapViewportState = rememberMapViewportState {
                            setCameraOptions {
                                zoom(15.0)
                                center(
                                    initialPoint ?: Point.fromLngLat(
                                        -75.6906164,
                                        4.5292671
                                    )
                                )
                                pitch(0.0)
                                bearing(0.0)
                            }
                        },
                        onMapClickListener = { point ->
                            pointClicked = point
                            latitude = point.latitude().toString()
                            longitude = point.longitude().toString()
                            true
                        },
                        style = { MapStyle(style = Style.STANDARD_SATELLITE) }
                    ) {
                        pointClicked?.let { point ->
                            PointAnnotation(
                                point = point)
                                {
                                    iconImage = marker
                                }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = {},
                            colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                        IconButton(
                            onClick = {
                                navController.navigate(
                                    route = AppScreens.CommentsScreen.createRoute(
                                        report.idReport
                                    )
                                )
                            },

                            colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                        Button(
                            onClick = { myReport.deleteReport(report.idReport, report.imageUrl!!) },
                        ) {
                            Text(text = "Eliminar")
                        }

                        if (isEditing) {
                            Button(
                                onClick = {
                                    isEditing = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(text = "Editar")
                            }
                        } else {
                            Button(
                                onClick = {
                                    if (title.isNotEmpty() && categoria.isNotEmpty() && description.isNotEmpty() && latitude.isNotEmpty()
                                        && longitude.isNotEmpty()
                                    ) {
                                        myReport.editReport(
                                            report.idReport,
                                            title,
                                            categoria,
                                            description,
                                            latitude,
                                            longitude
                                        )
                                        showDialog2 = true
                                        isEditing = true
                                    } else {
                                        showDialog = true
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(text = "Listo")
                            }
                        }
                    }
                    if (showDialog) {
                        BasicAlertDialog(
                            onDismissRequest = { showDialog = false }

                        ) {
                            Text(text = "Debe llenar todos los campos")

                            Button(
                                onClick = { showDialog = false }
                            ) {
                                Text(text = "Aceptar")
                            }
                        }
                    }
                    if (showDialog2) {
                        BasicAlertDialog(
                            onDismissRequest = { showDialog2 = false }

                        ) {
                            Text(text = "Se actualizo el reporte")

                            Button(
                                onClick = { showDialog2 = false }
                            ) {
                                Text(text = "Aceptar")
                            }
                        }

                    }
                }
            }
        }
    }
}








