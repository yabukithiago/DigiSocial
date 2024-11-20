package com.examples.digisocial.ui.view.home

import LoginViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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


@Composable
fun HomePageVoluntario(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    val privileged = viewModel.state.value.privileged

    // Chama fetchUserPermission apenas uma vez ao iniciar
    LaunchedEffect(Unit) {
        viewModel.fetchUserPermission { _ -> }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Exibe apenas para usuários privilegiados
        if (privileged == true) {
            // Botão de Registar Beneficiário
            Button(
                onClick = { navController.navigate("registerBeneficiary") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Registar Beneficiários", color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Icon(
                        imageVector = Icons.Filled.PersonAdd,
                        contentDescription = "Registar Beneficiários",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }

        // Botão de Editar Beneficiário - apenas para privilegiados
        if (privileged == true) {
            Button(
                onClick = { navController.navigate("readBeneficiary") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Editar Beneficiários", color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Editar Beneficiários",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }

        // Botão de Horários
        Button(
            onClick = { navController.navigate("horarios") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Horários", color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = "Horários",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        // Botão de Mensagens
        Button(
            onClick = { navController.navigate("mensagens") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Mensagens", color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    imageVector = Icons.Filled.Message,
                    contentDescription = "Mensagens",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        // Botão de Editar Informações
        Button(
            onClick = { navController.navigate("editar_informacoes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Editar Informações", color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Editar Informações",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de Logout
        Button(
            onClick = { viewModel.logout(onLogoutSuccess = { navController.navigate("login") }) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("Logout", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePageVoluntario() {
    DigiSocialTheme {
        HomePageVoluntario(navController = rememberNavController())
    }
}
