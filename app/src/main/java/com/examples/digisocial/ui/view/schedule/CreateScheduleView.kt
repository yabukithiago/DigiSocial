package com.examples.digisocial.ui.view.schedule

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.examples.digisocial.ui.components.bars.TopBar
import java.util.Calendar

@Composable
fun CreateScheduleView(navController: NavController) {
    val viewModel: CreateScheduleViewModel = viewModel()
    val state by viewModel.state
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    TopBar(title = "Criar Escalas", navController = navController)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(0.8f),
            value = state.vagasTotais.toString(),
            onValueChange = { newValue ->
                viewModel.onVagasTotaisChange(newValue.toIntOrNull() ?: 0)
            },
            label = { Text("Quantidade de vagas") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(0.77f),
            onClick = {
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
        }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6))) {
            Text(text = "Selecionar Data")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (state.vagasTotais > 0) {
                    viewModel.addSchedule(onSuccess = {
                        Toast.makeText(
                            context, "Escala criada com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.popBackStack()
                    }
                    )
                } else {
                    Toast.makeText(
                        context, "Preencha todos os campos.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6)),
            enabled = !state.isLoading
        ) {
            Text(if (state.isLoading) "Carregando..." else "Criar Escala")
        }

        if (state.errorMessage?.isNotEmpty() == true) {
            state.errorMessage?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
        }
    }
}