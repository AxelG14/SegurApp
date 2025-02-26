package com.example.projectappmovil

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectappmovil.Navegation.AppScreens


@Composable
fun imageLogin(){
    Image(
        painter = painterResource(R.drawable.vueloenavion),
        contentDescription = "Imagen Inicio",
        modifier = Modifier.size(200.dp),
    )
}

@Composable
fun titulo(){
    Text(
        text = "Bienvenidos Apps Moviles",
        color = MaterialTheme.colorScheme.primary,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun textField(){
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = {newText ->
            text = newText},
        label = { Text("EMAIL") }
    )

    Spacer(modifier = Modifier.height(20.dp))

    var text2 by remember { mutableStateOf("") }
    TextField(
        value = text2,
        onValueChange = {newText -> text2 = newText},
        label = { Text("PASSWORD") }
    )
}

@Composable
fun olvideContrasenia(){
    Text(
        text = "¿Olvidaste tu contraseña?",
        color = MaterialTheme.colorScheme.primary,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.clickable() {
        }
    )
}

@Composable
fun buttons(navController: NavController){
    Button(
        onClick = {},
        modifier = Modifier.size(height = 60.dp, width = 250.dp)
    ) {
        Text("INICIAR SESION")
    }

    Spacer(modifier = Modifier.height(20.dp))

    Button(
        onClick = {navController.navigate(route = AppScreens.RegisterScreen.route)},
        modifier = Modifier.size(height = 60.dp, width = 250.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text("REGISTRARSE")
    }
}

@Composable
fun previewLogin(navController: NavController){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(60.dp))
        imageLogin()
        titulo()
        Spacer(modifier = Modifier.height(80.dp))
        textField()
        Spacer(modifier = Modifier.height(20.dp))
        olvideContrasenia()
        Spacer(modifier = Modifier.height(100.dp))
        buttons(navController)
    }


}