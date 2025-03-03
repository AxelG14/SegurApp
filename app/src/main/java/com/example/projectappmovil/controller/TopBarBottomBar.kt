package com.example.projectappmovil.controller

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectappmovil.R
import com.example.projectappmovil.navegation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class TopBarBottomBar {

    val auth: FirebaseAuth = Firebase.auth
    val email = auth.currentUser?.email

    @Composable
    fun ButtomBar(navController: NavController){
        NavigationBar {
            NavigationBarItem(
                onClick = {},
                selected = false,
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                label = { Text("MENU") }
            )
            NavigationBarItem(
                onClick = {},
                selected = false,
                icon = { Icon(imageVector = Icons.Default.Place, contentDescription = null) },
                label = { Text("REPORTS") }
            )
            NavigationBarItem(
                onClick = {navController.navigate(route = AppScreens.ReportScreen.route)},
                selected = false,
                icon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                label = { Text("MINE") }
            )
            NavigationBarItem(
                onClick = {},
                selected = false,
                icon = { Icon(imageVector = Icons.Default.Notifications, contentDescription = null) },
                label = { Text("NOTIFI") }
            )
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar(){
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
                IconButton(
                    onClick = { },
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

    }
}
