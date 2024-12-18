package com.examples.digisocial.ui.view.visit

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun AttendanceRegisterView(navController: NavController, id: String) {
    val viewModel: AttendanceRegisterViewModel = viewModel()
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirma Presença") },
            text = { Text("O beneficiário está presente?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.attendanceRegister(id = id, onSuccess = { navController.popBackStack()})
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate("readBeneficiary") {
                        popUpTo("readBeneficiary") { inclusive = true }
                    }
                }) {
                    Text("Não")

                }
            }
        )
    }
}
