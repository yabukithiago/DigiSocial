package com.examples.digisocial.ui.view.finance

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.ui.theme.DigiSocialTheme

@Composable
fun AddTransactionView(navController: NavController) {
    val viewModel: AddTransactionViewModel = viewModel()
    val state by viewModel.state
    val context = LocalContext.current

    TopBar(title = "Adicionar Transação", navController = navController)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = state.description,
            onValueChange = viewModel::onDescriptionChange,
            label = { Text("Descrição") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.amount,
            onValueChange = viewModel::onAmountChange,
            label = { Text("Quantidade") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = state.type == "ENTRADA",
                    onClick = { viewModel.onTypeChange("ENTRADA") },
                    modifier = Modifier.scale(0.8f)
                )
                Text(
                    text = "Entrada"
                )

                RadioButton(
                    selected = state.type == "SAIDA",
                    onClick = { viewModel.onTypeChange("SAIDA") },
                    modifier = Modifier.scale(0.8f)
                )
                Text(
                    text = "Saída"
                )
            }
        }

        Button(onClick = {
            viewModel.addTransaction(
                state.description,
                state.amount.toDouble(),
                state.type,
                onSuccess = {
                    Toast.makeText(context, "Transação criada com sucesso", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                onFailure = { errorMessage ->
                    state.errorMessage = errorMessage
                }
            )
        },
            ) {
            Text("Salvar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddTransactionView() {
    DigiSocialTheme {
        AddTransactionView(navController = rememberNavController())
    }
}