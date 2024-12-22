package com.examples.digisocial.ui.view.home

import com.examples.digisocial.ui.view.login.LoginViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun HomePageAdminView(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .statusBarsPadding()
        ) {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = Color.Blue
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
                ) {
                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = {
                        expanded = false
                        viewModel.logout(onLogoutSuccess = {
                            navController.navigate("login")
                        })
                    }
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate("users") },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.6f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Utilizadores",
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = { navController.navigate("showTransaction") },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.6f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Transações",
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = { navController.navigate("users") },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.6f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Horários",
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = { navController.navigate("users") },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.6f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Relatórios",
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
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