package com.example.projectappmovil

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun password(){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "OLVIDASTE TU CONTRASEÑA?",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 25.dp),
            fontWeight = FontWeight.Bold
        )

        var email by remember { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = {newEmail -> email = newEmail},
            label = { Text("EMAIL")},
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(0.9f),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)}

        )

        var password by remember { mutableStateOf("") }
        TextField(
            value = password,
            onValueChange = {newPassword -> password = newPassword},
            label = { Text("NEW PASSWORD")},
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(0.9f),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)}
        )

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(text = "CONFIRMAR")
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(0.8f),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                
        ) {
            Text(text = "INICIAR SESIÓN")
        }
    }

}