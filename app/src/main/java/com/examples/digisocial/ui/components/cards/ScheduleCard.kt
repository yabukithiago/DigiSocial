package com.examples.digisocial.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.examples.digisocial.ui.components.InfoRow
import com.examples.digisocial.ui.view.login.LoginViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.util.Date

@Composable
fun ScheduleCard(
    navController: NavController, id: String, data: Date,
    vagasDisponiveis: Int, onClick: () -> Unit) {
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    var menuExpanded by remember { mutableStateOf(false) }
    val loginViewModel: LoginViewModel = viewModel()
    var role = ""

    if (currentUser != null) {
        loginViewModel.fetchUserRole(currentUser.uid) { role = it }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = data.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF333333),
                    maxLines = 1
                )
                InfoRow(icon = Icons.Default.Info, text = "Vagas disponíveis: $vagasDisponiveis")
            }

            Box(contentAlignment = Alignment.TopEnd) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    modifier = Modifier
                        .clickable { menuExpanded = true }
                        .padding(8.dp)
                )
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    if (role == "voluntary") {
                        DropdownMenuItem(
                            text = { Text("Inscrever-se") },
                            onClick = {
                                navController.navigate("addVoluntaryOnSchedule/$id")
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Cancelar Inscrição") },
                            onClick = {
                                navController.navigate("deleteVoluntaryOnSchedule/$id")
                                menuExpanded = false
                            }
                        )
                    }
                    if (role == "admin") {
                        DropdownMenuItem(
                            text = { Text("Excluir") },
                            onClick = {
                                navController.navigate("deleteSchedule/$id")
                                menuExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}