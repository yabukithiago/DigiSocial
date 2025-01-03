package com.examples.digisocial.ui.components.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BottomBar(navController: NavController, userRole: String) {
    BottomAppBar {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround) {
            when (userRole) {
                "admin" -> {
                    IconButton(onClick = { navController.navigate("homeAdmin") }) {
                        Icon(Icons.Default.Home, contentDescription = "Home Admin")
                    }
                    IconButton(onClick = { navController.navigate("readBeneficiary") }) {
                        Icon(Icons.Default.SupervisorAccount, contentDescription = "Manage Beneficiary")
                    }
                    IconButton(onClick = { navController.navigate("showDashboard") }) {
                        Icon(Icons.Default.Analytics, contentDescription = "Show Dashboard")
                    }
                }
                "voluntary" -> {
                    IconButton(onClick = { navController.navigate("homeVoluntary") }) {
                        Icon(Icons.Default.Home, contentDescription = "Home Voluntary")
                    }
                    IconButton(onClick = { navController.navigate("readBeneficiary") }) {
                        Icon(Icons.Default.SupervisorAccount, contentDescription = "Manage Beneficiary")
                    }
                }
                "juntamember" -> {
                    IconButton(onClick = { navController.navigate("homeJuntaMember") }) {
                        Icon(Icons.Default.Home, contentDescription = "Home Junta")
                    }
                    IconButton(onClick = { navController.navigate("homeJuntaMember") }) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Requests")
                    }
                }
                else -> {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Home, contentDescription = "Default Home")
                    }
                }
            }
        }
    }
}