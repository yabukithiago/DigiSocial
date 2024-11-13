package com.examples.digisocial.ui.view.home

import LoginViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.theme.DigiSocialTheme

@Composable
fun HomePageAdminView(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel : LoginViewModel = viewModel()
    val state by viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.navigate("users") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(80.dp)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("Utilizadores")
                }
                Button(
                    onClick = { /* TODO: Action for button 1 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(80.dp)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("Financeiro")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { /* TODO: Action for button 1 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(80.dp)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("Relatórios")
                }
                Button(
                    onClick = { /* TODO: Action for button 1 */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .height(80.dp)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("Configurações")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.logout(onLogoutSuccess = { navController.navigate("login") }) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
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