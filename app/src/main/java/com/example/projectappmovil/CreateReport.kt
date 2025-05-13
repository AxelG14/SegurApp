package com.example.projectappmovil
import androidx.activity.compose.rememberLauncherForActivityResult
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.projectappmovil.controller.CreateReportController
import com.example.projectappmovil.controller.NotificationController
import com.example.projectappmovil.navegation.AppScreens
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.IconImage
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.compose.style.MapStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReport(navController: NavController){
    val db = Firebase.firestore
    val auth = Firebase.auth
    val user = auth.currentUser

    val context = LocalContext.current
    val permission = android.Manifest.permission.ACCESS_FINE_LOCATION

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
        Toast.makeText(
            context,
            if (isGranted) "Permiso concedido" else "Permiso denegado",
            Toast.LENGTH_SHORT
        ).show()
    }

    LaunchedEffect(hasPermission) {
        if (!hasPermission) {
            permissionLauncher.launch(permission)
        } else {
            val fusedClient = LocationServices.getFusedLocationProviderClient(context)
            fusedClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    Log.d("Map", "Lat: ${it.latitude}, Lon: ${it.longitude}")
                } ?: Log.w("Map", "Ubicación nula")
            }
        }
    }

    Scaffold (
        bottomBar = {
            NavigationBar { NavigationBarItem(
                onClick = {navController.navigate(route = AppScreens.InicioScreen.route)},
                selected = false,
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                label = { Text("MENU") }
            )
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.AllReportsScreen.route)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Place, contentDescription = null) },
                    label = { Text("REPORTS") }
                )
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.MyReportsScreen.route)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                    label = { Text("MINE") }
                )
                NavigationBarItem(
                    onClick = {navController.navigate(route = AppScreens.ProfileScreen.route)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Notifications, contentDescription = null) },
                    label = { Text("PROFILE") }
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
            val categorias =
                listOf("Seguridad", "Infraestructura", "Mascotas", "Comunidad")
            var categoria by remember { mutableStateOf("") }
            var expanded by remember { mutableStateOf(false) }
            var descripcion by remember { mutableStateOf("") }
            var ubicacion by remember { mutableStateOf("") }
            var nombre by remember { mutableStateOf("") }
            var tituloError by remember { mutableStateOf(false) }
            var categoriaError by remember { mutableStateOf(false) }
            var descripcionError by remember { mutableStateOf(false) }
            var ubicacionError by remember { mutableStateOf(false) }

            LaunchedEffect(user?.email) {
                db.collection("usuarios")
                    .whereEqualTo("email", user?.email)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty){
                            val document = querySnapshot.documents[0]
                            nombre = document.getString("nombre") ?: ""
                        }
                    }
            }

            TextField(
                value = titulo,
                onValueChange = { newTitulo ->
                    titulo = newTitulo
                    tituloError = newTitulo.isEmpty()
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .border(2.dp, if (tituloError) Color.Red else Color.Transparent, RoundedCornerShape(4.dp)),
                label = { Text("Titulo") }
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(0.9f).padding(vertical = 8.dp)
            ) {
                TextField(
                    value = categoria,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Categoria") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .border(2.dp, if (categoriaError) Color.Red else Color.Transparent, RoundedCornerShape(4.dp))
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
                                categoriaError = false
                                expanded = false
                            }
                        )
                    }
                }
            }

            TextField(
                value = descripcion,
                onValueChange = { newDescripcion ->
                    descripcion = newDescripcion
                    descripcionError = newDescripcion.isEmpty()
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .border(2.dp, if (descripcionError) Color.Red else Color.Transparent, RoundedCornerShape(4.dp)),
                label = { Text("Descripcion") }
            )

            var pointClicked by remember { mutableStateOf<Point?>(null) }

            var markerResourceId by remember { mutableStateOf(R.drawable.red_marker) }

            var marker = rememberIconImage(key = markerResourceId, painter = painterResource(markerResourceId ))

            MapboxMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                mapViewportState = rememberMapViewportState {
                    setCameraOptions {
                        zoom(11.0)
                        center(Point.fromLngLat(-75.6906164, 4.5292671))
                        pitch(0.0)
                        bearing(0.0)
                    }
                },
                onMapClickListener = { point ->
                    pointClicked = point
                    true
                },
                style = { MapStyle(style = Style.STANDARD_SATELLITE) }
            ) {
                pointClicked?.let { clickedPoint ->
                    PointAnnotation(
                        point = pointClicked!!
                    ) {
                        iconImage = marker
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            var imageUri by remember { mutableStateOf<Uri?>(null) }

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
                    modifier = Modifier.size(120.dp)
                )
            } else {
                Text("No image selected")
            }

            Button(
                onClick = { launcher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Select Image from Gallery")
            }

            Spacer(modifier = Modifier.height(10.dp))

            val createReportController = CreateReportController()
            val count = NotificationController()
            val currentUser = FirebaseAuth.getInstance()
            val userId = currentUser.uid
            val email = currentUser.currentUser?.email

            var showDialog by remember { mutableStateOf(false) }
            var showDialog2 by remember { mutableStateOf(false) }
            Button(
                onClick = {
                    tituloError = titulo.isEmpty()
                    categoriaError = categoria.isEmpty()
                    descripcionError = descripcion.isEmpty()
                    ubicacionError = ubicacion.isEmpty()

                    if (tituloError || categoriaError || descripcionError || ubicacionError) {
                        showDialog = true
                    } else {
                        if (userId != null && imageUri != null && email != null) {
                            imageUri?.let { uri ->
                                createReportController.saveReportImageToFirebaseStorage(
                                    userId, titulo, categoria, descripcion, uri, nombre, pointClicked!!.latitude()
                                        .dec(), pointClicked!!.longitude().inc()
                                )
                                CreateReportController.GlobalData.notification.value += 1

                                showDialog2 = true
                                titulo = ""
                                categoria = ""
                                descripcion = ""
                                ubicacion = ""
                                imageUri = null
                            }
                        }
                    }
                },
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(0.9f)
            ) {
                Text(text = "CREAR REPORTE")

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
                    title = { Text("CORRECTO") },
                    text = { Text("Se creó el reporte correctamente!") },
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







