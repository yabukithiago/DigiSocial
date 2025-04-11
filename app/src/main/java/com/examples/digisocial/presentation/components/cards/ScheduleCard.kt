package com.examples.digisocial.presentation.components.cards

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
import com.examples.digisocial.domain.models.Schedule
import com.examples.digisocial.domain.models.Voluntary
import com.examples.digisocial.presentation.components.InfoRow
import com.examples.digisocial.ui.view.login.LoginViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ScheduleCard(schedule: Schedule, onClick: () -> Unit, onVoluntaryRegistration: (Voluntary) -> Unit, onDeleteVoluntary: (Voluntary) -> Unit, onEditSchedule: () -> Unit, onDeleteSchedule:() -> Unit, ) {
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    var menuExpanded by remember { mutableStateOf(false) }
    val loginViewModel: LoginViewModel = viewModel()
    var role = ""
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formattedDate = formatter.format(schedule.data)

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
                InfoRow(icon = Icons.Default.Info, text = formattedDate)
                InfoRow(icon = Icons.Default.Info, text = "Vagas Disponiveis: ${schedule.vagasDisponiveis}")
                InfoRow(icon = Icons.Default.Info, text = "Vagas Totais: ${schedule.vagasTotais}")
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
                                onVoluntaryRegistration(Voluntary(
                                    id = "",
                                    nome = "",
                                    telefone = "",
                                    email = "",
                                ))
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Cancelar Inscrição") },
                            onClick = {
                                onDeleteVoluntary(Voluntary(
                                    id = "",
                                    nome = "",
                                    telefone = "",
                                    email = "",
                                ))
                                menuExpanded = false
                            }
                        )
                    }
                    if (role == "admin") {
                        DropdownMenuItem(
                            text = { Text("Excluir") },
                            onClick = {
                                onDeleteSchedule()
                                menuExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}