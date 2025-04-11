package com.examples.digisocial.ui.view.schedule

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.examples.digisocial.R
import com.examples.digisocial.domain.models.Schedule
import java.util.Calendar

@Composable
fun CreateScheduleView(onDismiss: () -> Unit, onCreateSchedule: (Schedule) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var selectedDate by remember { mutableStateOf<Calendar?>(null) }
    var vagasTotais by remember { mutableLongStateOf(0L) }
    var vagasDisponiveis by remember { mutableLongStateOf(0L) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Registar Beneficiário")
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(0.77f),
                    onClick = {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                val newDate = Calendar.getInstance().apply {
                                    set(year, month, dayOfMonth)
                                }
                                selectedDate = newDate
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6))
                ) {
                    Text(text = "Selecionar Data")
                }

                TextField(
                    value = vagasTotais.toString(),
                    onValueChange = { input ->
                        val numericValue = input.toLongOrNull()
                        if (numericValue != null) {
                            vagasTotais = numericValue
                        } else {
                            Toast.makeText(
                                context, "Número de vagas deve ser um número",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    label = { Text("Vagas Totais") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = vagasDisponiveis.toString(),
                    onValueChange = { input ->
                        val numericValue = input.toLongOrNull()
                        if (numericValue != null) {
                            vagasDisponiveis = numericValue
                        } else {
                            Toast.makeText(
                                context, "Número de vagas deve ser um número",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    label = { Text("Vagas Disponíveis") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (vagasTotais > 0 && vagasDisponiveis > 0) {
                        onCreateSchedule(
                            Schedule(
                                id = "",
                                data = calendar.time,
                                vagasTotais = vagasTotais.toInt(),
                                vagasDisponiveis = vagasDisponiveis.toInt()
                            )
                        )
                    } else {
                        Toast.makeText(context, R.string.empty_fields, Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6))
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6))
            ) {
                Text("Cancelar")
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}