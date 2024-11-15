package com.examples.digisocial.ui.view.delete

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.examples.digisocial.repository.BeneficiaryRepository

@Composable
fun DeleteBeneficiaryView(navController: NavController, id: String) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
       AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar Exclusão") },
            text = { Text("Tem certeza de que deseja excluir este beneficiário?") },
            confirmButton = {
               TextButton(onClick = {
                    BeneficiaryRepository.deleteBeneficiary(
                        id = id,
                        onSuccess = {
                            showDialog = false
                            navController.popBackStack()
                        },
                        onFailure = { exception ->
                            showDialog = false
                            Log.e("DeleteBeneficiary", "Erro ao excluir: ${exception.message}")
                        }
                    )
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Não")
                }
            }
        )
    }
}
