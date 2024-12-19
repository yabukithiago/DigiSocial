package com.examples.digisocial.ui.view.visit

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun VisitRegisterView(navController: NavController, id: String) {
    val viewModel: VisitRegisterViewModel = viewModel()
    var showDialog by remember { mutableStateOf(true) }
    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirma Presença") },
            text = { Text("O beneficiário está presente?") },
            confirmButton = {
                TextButton(onClick = {
                    Toast.makeText(context, "Visita registrada com sucesso", Toast.LENGTH_SHORT).show()
                    viewModel.visitRegister(id = id, onSuccess = { navController.popBackStack()})
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
