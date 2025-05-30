package com.example.projectappmovil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.projectappmovil.controller.CreateReportController
import com.example.projectappmovil.navegation.AppScreens
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
fun AllReports(navController: NavHostController) {
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
                    selected = true,
                    icon = { Icon(imageVector = Icons.Default.Place, contentDescription = null) },
                    label = { Text("REPORTES") }
                )
                NavigationBarItem(
                    onClick = { navController.navigate(route = AppScreens.MyReportsScreen.route) },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                    label = { Text("PROPIOS") }
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
                        "Reportes",
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
        var expanded by remember { mutableStateOf(false) }
        val categorias =
            listOf("Todos", "Seguridad", "Infraestructura", "Mascotas", "Comunidad", "Emergencia")
        var categoria by remember { mutableStateOf(categorias.first()) }
        Column(
            modifier = Modifier
                .background(Color.Black)
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 8.dp)
            ) {
                TextField(
                    value = categoria,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Categoria") },
                    shape = MaterialTheme.shapes.medium,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.medium
                        )
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
        }
        LoadImageFromFirestore3(innerPadding, navController, categoria)
    }
}

@Composable
fun Badges(count: Int) {
    if (count > 0) {
        Box(
            modifier = Modifier
                .padding(bottom = 2.dp)
                .offset(x = 9.dp, y = (-9).dp)
                .clip(CircleShape)
                .background(Color.Red)
                .size(15.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count.toString(),
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun LoadImageFromFirestore3(
    innerPadding: PaddingValues,
    navController: NavController,
    categoria: String
) {
    val db = Firebase.firestore
    var reports by remember { mutableStateOf<List<Report2>>(emptyList()) }
    LaunchedEffect(categoria) {
        db.collection("reportes")
            .addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
                if (exception != null) {
                    println("Error listening to Firestore: ${exception.message}")
                    return@addSnapshotListener
                }
                val newReports = snapshot?.documents?.mapNotNull { document ->
                    Report2(
                        imageUrl = document.getString("imageUrl"),
                        title = document.getString("titulo") ?: "",
                        categoria = document.getString("categoria") ?: "",
                        description = document.getString("descripcion") ?: "",
                        latitude = document.getString("latitude") ?: "",
                        longitude = document.getString("longitude") ?: "",
                        nombre = document.getString("nombre") ?: "",
                        idReport = document.getString("idReport") ?: "",
                        check = document.getBoolean("check") ?: false,
                        countMessages = document.getLong("countMessages")?.toInt() ?: 0

                    )
                } ?: emptyList()
                reports = when (categoria) {
                    "Seguridad" -> newReports.filter { it.categoria == "Seguridad" }
                    "Infraestructura" -> newReports.filter { it.categoria == "Infraestructura" }
                    "Mascotas" -> newReports.filter { it.categoria == "Mascotas" }
                    "Comunidad" -> newReports.filter { it.categoria == "Comunidad" }
                    "Emergencia" -> newReports.filter { it.categoria == "Emergencia" }
                    else -> newReports
                }
                val categorias =
                    listOf(
                        "Todos",
                        "Seguridad",
                        "Infraestructura",
                        "Mascotas",
                        "Comunidad",
                        "Emergencia"
                    )
            }
    }
    MyLazyColumn2(reports = reports, innerPadding, navController)
}

data class Report2(
    val imageUrl: String?,
    val title: String,
    val categoria: String,
    val description: String,
    val latitude: String,
    val longitude: String,
    val nombre: String,
    val idReport: String,
    val check: Boolean,
    val countMessages: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLazyColumn2(
    reports: List<Report2>,
    innerPadding: PaddingValues,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 80.dp)
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
                            .height(150.dp)
                    )

                }
                Column(
                    modifier = Modifier
                        .background(color = Color.DarkGray),
                ) {
                    Text(
                        text = "Titulo: " + report.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                    Text(
                        text = "Categoria: " + report.categoria,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Text(
                        text = "Descripcion: " + report.description,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Text(
                        text = "Ubicación: ",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
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
                            .padding(horizontal = 10.dp)
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
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {},
                            colors = IconButtonDefaults.iconButtonColors(Color.Transparent),
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
                            Badges(report.countMessages)
                        }

                        Row {
                            Text(
                                "Verificado",
                                modifier = Modifier
                                    .padding(top = 12.dp, start = 7.dp)
                            )
                            Checkbox(
                                checked = report.check,
                                onCheckedChange = {},

                                )
                        }
                    }
                }
            }
        }
    }
}





