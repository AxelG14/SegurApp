@file:Suppress("MissingPermission")

package com.example.projectappmovil


import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.projectappmovil.controller.CreateReportController
import com.example.projectappmovil.controller.NotificationController
import com.example.projectappmovil.navegation.AppScreens
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inicio(navController: NavController) {

    val mapViewportState = rememberMapViewportState()
    val context = LocalContext.current

    val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, permission) ==
                    android.content.pm.PackageManager.PERMISSION_GRANTED
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

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(permission)
        }
    }

    val countNotifi = NotificationController()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Inicio", fontSize = 20.sp) },
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.logo2),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                },
                actions = {
                    SmallFloatingActionButton(
                        onClick = {
                            navController.navigate(route = AppScreens.NotificationScreen.route)
                            countNotifi.updateClear(userId!!)
                        },
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = null, modifier = Modifier.size(30.dp))
                        Badges(CreateReportController.GlobalData.notification.value)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("MENU") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(AppScreens.AllReportsScreen.route) },
                    icon = { Icon(Icons.Default.Place, contentDescription = null) },
                    label = { Text("REPORTES") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(AppScreens.MyReportsScreen.route) },
                    icon = { Icon(Icons.Default.Create, contentDescription = null) },
                    label = { Text("PROPIOS") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(AppScreens.ProfileScreen.route) },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("PERFIL") }
                )
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var searchQuery by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    placeholder = { Text("Buscar...") },
                    shape = MaterialTheme.shapes.medium,
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.White
                    )
                )


                var pointClicked by remember { mutableStateOf<Point?>(null) }
                var markerResourceId by remember { mutableStateOf(R.drawable.red_marker) }
                var marker = rememberIconImage(key = markerResourceId, painter = painterResource(markerResourceId))

                val mapViewportState = rememberMapViewportState {
                    setCameraOptions {
                        center(Point.fromLngLat(-74.0817, 4.7110))
                        zoom(5.0)
                    }
                }

                MapboxMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(430.dp),
                    mapViewportState = mapViewportState,
                    onMapClickListener = { point ->
                        pointClicked = point
                        true
                    },
                    style = { MapStyle(style = Style.STANDARD_SATELLITE) }
                ) {
                    MapEffect(pointClicked) { mapView ->
                        mapView.location.updateSettings {
                            locationPuck = createDefault2DPuck(withBearing = true)
                            enabled = true
                        }
                    }

                }

                Spacer(modifier = Modifier.height(5.dp))

                Button(
                    onClick = { navController.navigate(AppScreens.CreateReportScreen.route) },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .height(50.dp)
                        .width(300.dp),
                    border = BorderStroke(1.dp, Color.White)
                ) {
                    Text("CREAR REPORTE", fontWeight = FontWeight.Bold)
                }

            }
        }
    )
}
