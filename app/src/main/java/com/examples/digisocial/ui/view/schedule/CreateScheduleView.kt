package com.examples.digisocial.ui.view.schedule

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.util.Calendar

@Composable
fun CreateScheduleView(navController: NavController, id: String) {
    val viewModel: CreateScheduleViewModel = viewModel()
    val state by viewModel.state
    var showDialog by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Atribuir Escala") },
            text = {
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Text("Deseja atribuir esse voluntário à escala?")

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = state.tarefa,
                        onValueChange = viewModel::onTarefaChange,
                        label = { Text("Descrição da Tarefa") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                calendar.set(year, month, dayOfMonth)
                                viewModel.onDateChange(calendar.time)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }) {
                        Text(text = "Selecionar Data")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (state.tarefa.isNotBlank()) {
                        Toast.makeText(context, "Escala atribuída com sucesso", Toast.LENGTH_SHORT).show()
                        viewModel.addSchedule(
                            id = id,
                            onSuccess = { navController.popBackStack() }
                        )
                    } else {
                        Toast.makeText(context, "Por favor, insira a descrição da tarefa", Toast.LENGTH_SHORT).show()
                    }
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