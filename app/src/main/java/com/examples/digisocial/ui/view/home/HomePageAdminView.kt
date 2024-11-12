package com.examples.digisocial.ui.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.theme.DigiSocialTheme

@Composable
fun HomePageAdminView(navController: NavController, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("registerVolunteer") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Registar Voluntário")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("registerManager") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
               Text("Registar Voluntário Gestor")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("registerBeneficiary") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Registar Beneficiário")
            }
        }
        Button(
            onClick = {
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
                      },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
            ) {
            Text("Logout")
        }
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewHomePageAdminView() {
    DigiSocialTheme {
        HomePageAdminView(navController = rememberNavController())
    }
}