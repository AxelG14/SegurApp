package com.example.projectappmovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectappmovil.Navegation.AppScreens
import com.example.projectappmovil.ui.theme.ProjectAppMovilTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class Inicio : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectAppMovilTheme {
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun inicio2(navController: NavController){
    val auth: FirebaseAuth = Firebase.auth
    val email = auth.currentUser?.email
    Scaffold (
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
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
        },

        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("$email")},
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.vueloenavion),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                },
                actions = {
                    IconButton(
                        onClick = { /* Aquí va la acción al hacer clic */ },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.Black,
                            containerColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray)

            )
        },
        content = { innerPadding ->
            Column (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            )   {

                Spacer(modifier = Modifier.height(460.dp))
                Button(
                    onClick = {navController.navigate(route = AppScreens.CreateReportScreen.route)},
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .size(height = 50.dp, width = 300.dp),
                    border = BorderStroke(1.dp, Color.Blue)
                ) {
                    Text("CREAR REPORTE")
                }
            }
        }
    )
}


//@Composable
//fun inicio(navController: NavController){
//    Row (
//        modifier = Modifier.fillMaxSize().padding(top = 20.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//    )
//    {
//        image()
//        titleInicio()
//        notification()
//    }
//    Column (
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier.height(80.dp))
//        line()
//        Spacer(modifier = Modifier.height(450.dp))
//        btnCreateReport(navController)
//        Spacer(modifier = Modifier.height(20.dp))
//        navigationBar()
//    }
//}