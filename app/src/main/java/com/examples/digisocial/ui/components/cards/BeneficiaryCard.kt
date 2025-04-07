package com.examples.digisocial.ui.components.cards

import com.examples.digisocial.ui.view.login.LoginViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.SafetyDivider
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
import com.examples.digisocial.domain.models.Beneficiary
import com.examples.digisocial.domain.models.Visit
import com.examples.digisocial.ui.components.InfoRow
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.util.Date

@Composable
fun BeneficiaryCard(
    beneficiary: Beneficiary, onClick: () -> Unit, onVisitRegistration: (Visit) -> Unit,
    onEditBeneficiary: () -> Unit, onDeleteBeneficiary: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val loginViewModel: LoginViewModel = viewModel()
    var role = ""
    var privileged = false

    if (currentUser != null) {
        loginViewModel.fetchUserRole(currentUser.uid) { role = it }
        loginViewModel.fetchUserPermission {
            if (it != null) {
                privileged = it
            }
        }
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
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFFE0E0E0), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Person Icon",
                    tint = Color(0xFF757575),
                    modifier = Modifier.size(30.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = beneficiary.nome,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF333333),
                        maxLines = 1
                    )
                    InfoRow(icon = Icons.Default.Phone, text = beneficiary.telemovel)
                    InfoRow(icon = Icons.Default.Person, text = beneficiary.referencia)
                    InfoRow(
                        icon = Icons.Default.Face,
                        text = beneficiary.agregadoFamiliar.toString()
                    )
                    InfoRow(icon = Icons.Default.LocationOn, text = beneficiary.nacionalidade)
                    InfoRow(icon = Icons.Default.BookmarkBorder, text = beneficiary.pedidos)
                    InfoRow(
                        icon = Icons.Default.SafetyDivider,
                        text = beneficiary.numeroVisitas.toString()
                    )
                    InfoRow(icon = Icons.Default.Done, text = beneficiary.ownerId)
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
                        DropdownMenuItem(
                            text = { Text("Registar Presença") },
                            onClick = {
                                onVisitRegistration(
                                    Visit(
                                        id = "",
                                        data = Date()
                                    )
                                )
                                menuExpanded = false
                            }
                        )
                        if (role == "admin" || privileged) {
                            DropdownMenuItem(
                                text = { Text("Editar") },
                                onClick = {
                                    onEditBeneficiary()
                                    menuExpanded = false
                                }
                            )
                            if (role == "admin") {
                                DropdownMenuItem(
                                    text = { Text("Excluir") },
                                    onClick = {
                                        onDeleteBeneficiary()
                                        menuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}