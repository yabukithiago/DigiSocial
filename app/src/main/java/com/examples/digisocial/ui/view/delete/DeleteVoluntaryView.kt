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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.examples.digisocial.presentation.Screen
import com.examples.digisocial.presentation.voluntary_list.VoluntaryListViewModel

@Composable
fun DeleteVoluntaryView(navController: NavController, id: String, viewModel: VoluntaryListViewModel = hiltViewModel()) {
    var showDialog by remember { mutableStateOf(true) }
    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar Exclusão") },
            text = { Text("Tem certeza de que deseja excluir este voluntário?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteVoluntary(id)
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate(route = Screen.VoluntaryListScreen.route) {
                        popUpTo(route = Screen.VoluntaryListScreen.route) { inclusive = true }
                    }
                }) {
                    Text("Não")
                }
            }
        )
    }
}
