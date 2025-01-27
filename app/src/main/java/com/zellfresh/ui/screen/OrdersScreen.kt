package com.zellfresh.ui.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun OrdersScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Products Page",
            modifier = modifier
        )
        IconButton(onClick = { navController.navigate("home") }, modifier = modifier) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon", modifier = modifier)
        }
    }

}