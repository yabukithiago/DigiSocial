package com.examples.digisocial.ui.view.delete

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
import com.examples.digisocial.ui.view.edit.DeleteUserViewModel

@Composable
fun DeleteUserView(navController: NavController, id: String) {
    val viewModel: DeleteUserViewModel = viewModel()
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar Exclusão") },
            text = { Text("Tem certeza de que deseja excluir este beneficiário?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteUser(id = id, onSuccess = { navController.navigate("readPendingUser")})
                    }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate("readPendingUser") {
                        popUpTo("readPendingUser") { inclusive = true }
                    }
                }) {
                    Text("Não")

                }
            }
        )
    }
}
