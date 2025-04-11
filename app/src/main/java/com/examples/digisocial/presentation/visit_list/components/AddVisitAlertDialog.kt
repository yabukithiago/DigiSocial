package com.examples.digisocial.presentation.visit_list.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AddVisitAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Adicionar Voluntário") },
        text = { Text(text = "O beneficiário está presente?") },
        confirmButton = {
            Button(onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6))) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6))) {
                Text("Cancelar")
            }
        }
    )
}
