package com.examples.digisocial.ui.view.delete

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.examples.digisocial.presentation.Screen
import com.examples.digisocial.presentation.beneficiary_list.BeneficiaryListViewModel

@Composable
fun DeleteBeneficiaryView(navController: NavController, id: String, viewModel: BeneficiaryListViewModel = hiltViewModel()) {
    var showDialog by remember { mutableStateOf(true) }
    LocalContext.current

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar Exclusão") },
            text = { Text("Tem certeza de que deseja excluir este beneficiário?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteBeneficiary(id)
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate(route = Screen.BeneficiaryListScreen.route) {
                        popUpTo(route = Screen.BeneficiaryListScreen.route) { inclusive = true }
                    }
                }) {
                    Text("Não")
                }
            }
        )
    }
}
