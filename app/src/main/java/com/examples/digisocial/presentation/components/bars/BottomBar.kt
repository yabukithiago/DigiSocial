package com.examples.digisocial.presentation.components.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.examples.digisocial.presentation.Screen

@Composable
fun BottomBar(navController: NavController, userRole: String) {
    BottomAppBar {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround) {
            when (userRole) {
                "admin" -> {
                    IconButton(onClick = { navController.navigate(Screen.HomePageAdminScreen.route) }) {
                        Icon(Icons.Default.Home, contentDescription = "Home Admin")
                    }
                    IconButton(onClick = { navController.navigate(Screen.BeneficiaryListScreen.route) }) {
                        Icon(Icons.Default.SupervisorAccount, contentDescription = "Manage Beneficiary")
                    }
                    IconButton(onClick = { navController.navigate("showDashboard") }) {
                        Icon(Icons.Default.PieChart, contentDescription = "Show Dashboard")
                    }
                }
                "voluntary" -> {
                    IconButton(onClick = { navController.navigate(Screen.HomePageVoluntaryScreen.route) }) {
                        Icon(Icons.Default.Home, contentDescription = "Home Voluntary")
                    }
                    IconButton(onClick = { navController.navigate(Screen.BeneficiaryListScreen.route) }) {
                        Icon(Icons.Default.SupervisorAccount, contentDescription = "Manage Beneficiary")
                    }
                }
                "juntamember" -> {
                    IconButton(onClick = { navController.navigate(Screen.HomePageJuntaMemberScreen.route) }) {
                        Icon(Icons.Default.Home, contentDescription = "Home Junta")
                    }
                    IconButton(onClick = { navController.navigate(Screen.HomePageJuntaMemberScreen.route) }) {
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