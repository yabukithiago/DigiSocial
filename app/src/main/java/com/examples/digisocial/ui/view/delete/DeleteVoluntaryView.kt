package com.examples.digisocial.ui.view.delete

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
fun DeleteVoluntaryView(navController: NavController, id: String) {
    val viewModel: DeleteVoluntaryViewModel = viewModel()
    var showDialog by remember { mutableStateOf(true) }
    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar Exclusão") },
            text = { Text("Tem certeza de que deseja excluir este voluntário?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteVoluntary(id = id, onSuccess = {
                        Toast.makeText(context, "Voluntário excluído com sucesso", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()})
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate("readVoluntary") {
                        popUpTo("readVoluntary") { inclusive = true }
                    }
                }) {
                    Text("Não")
                }
            }
        )
    }
}
