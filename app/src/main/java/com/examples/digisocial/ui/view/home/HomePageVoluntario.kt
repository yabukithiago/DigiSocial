package com.examples.digisocial.ui.view.home

import com.examples.digisocial.ui.view.login.LoginViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.theme.DigiSocialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.examples.digisocial.ui.components.bars.BottomBar

@Composable
fun HomePageVoluntario(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    val privileged = viewModel.state.value.privileged
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BottomBar(navController = navController, userRole = "voluntary") },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchUserPermission { _ -> }
    }
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

        if (privileged == true) {
            Button(
                onClick = { navController.navigate("readBeneficiary") },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(0.6f)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Beneficiarios", color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar Beneficiários",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
        }

        Button(
            onClick = { navController.navigate("readBeneficiary") },
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
                    "Registar Presença",
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = { navController.navigate("horarios") },
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
            onClick = { navController.navigate("mensagens") },
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
                    "Mensagens",
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = { navController.navigate("editar_informacoes") },
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
                    "Editar Informações",
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePageVoluntarioPreview() {
    DigiSocialTheme {
        HomePageVoluntario(navController = rememberNavController())
    }
}
