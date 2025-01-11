package com.examples.digisocial.ui.view.schedulevoluntary

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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun DeleteVoluntaryScheduleView(navController: NavController, id: String) {
    val viewModel: DeleteVoluntaryScheduleViewModel = viewModel()
    var showDialog by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val auth = Firebase.auth
    val currentUser = auth.currentUser?.uid

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Cancels Inscrição") },
            text = { Text("Quer mesmo se desinscrever?") },
            confirmButton = {
                TextButton(onClick = {
                    if (currentUser != null) {
                        viewModel.removeVoluntaryFromSchedule(id = id, voluntaryId = currentUser,
                            onSuccess = {
                                Toast.makeText(context, "Você se desinscreveu!", Toast.LENGTH_SHORT)
                                    .show()
                                navController.popBackStack()
                            },
                            onFailure = {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            })
                    }
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    navController.navigate("readSchedule") {
                        popUpTo("readSchedule") { inclusive = true }
                    }
                }) {
                    Text("Não")

                }
            }
        )
    }
}