package com.example.projectappmovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectappmovil.ui.theme.ProjectAppMovilTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class Inicio : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectAppMovilTheme {
                 preview3()
            }
        }
    }
}

@Composable
fun image(){
    Image(
        painter = painterResource(R.drawable.vueloenavion),
        contentDescription = null,
        modifier = Modifier.size(70.dp)
    )
}

@Composable
fun titleInicio(){
    Text(
        text = "BIENVENIDO",
        fontSize = 24.sp,
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun close(){
    IconButton(
        onClick = {},
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.primary),


        ) {
        Icon(
            imageVector = Icons.Default.Notifications, contentDescription = null,
            modifier = Modifier.size(50.dp))
    }
}

@Composable
fun line(){
    Text("_________________________________________",
    fontSize = 20.sp,
        color = MaterialTheme.colorScheme.primary
        )
}

@Composable
fun btnCreateReport(){
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        modifier = Modifier.size(height = 60.dp, width = 300.dp)
    ) {
        Text("CREAR REPORTE")
    }
}

@Composable
fun navigationBar(){
    NavigationBar {
        NavigationBarItem(
            onClick = {},
            selected = true,
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
            label = {Text("INICIO")}
        )
        NavigationBarItem(
            onClick = {},
            selected = false,
            icon = { Icon(imageVector = Icons.Default.Place, contentDescription = null) },
            label = {Text("REPORTES")}
        )
        NavigationBarItem(
            onClick = {},
            selected = false,
            icon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
            label = {Text("MI REPORT")}
        )
        NavigationBarItem(
            onClick = {},
            selected = false,
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            label = {Text("MI PERFIL")}
        )
    }
}

@Preview
@Composable
fun preview3(){
    Row (
        modifier = Modifier.fillMaxSize().padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        image()
        titleInicio()
        close()
    }
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        line()
        Spacer(modifier = Modifier.height(450.dp))
        btnCreateReport()
        Spacer(modifier = Modifier.height(20.dp))
        navigationBar()
    }
}