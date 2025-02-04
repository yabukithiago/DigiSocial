package com.examples.digisocial.ui.view.create

import android.widget.Toast
import com.examples.digisocial.ui.components.NacionalidadeDropdownMenu
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.R
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.ui.theme.DigiSocialTheme

@Composable
fun CreateBeneficiaryView(navController: NavController) {
    val viewModel: CreateBeneficiaryViewModel = viewModel()
    val state by viewModel.state
    val context = LocalContext.current

    TopBar(title = "Registar Beneficiários", navController = navController)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(id = R.drawable.digisocial),
            contentDescription = "User Icon"
        )

        TextField(
            value = state.nome,
            onValueChange = viewModel::onNomeChange,
            label = { Text("Nome") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.telemovel,
            onValueChange = viewModel::onTelemovelChange,
            label = { Text("Telemovel") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.referencia,
            onValueChange = viewModel::onReferenciaChange,
            label = { Text("Referência") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.agregadoFamiliar.toString(),
            onValueChange = { input ->
                val numericValue = input.toLongOrNull()
                if (numericValue != null) {
                    viewModel.onAgregadoFamiliarChange(numericValue)
                } else {
                    Toast.makeText(context, "Agregado Familiar deve ser um número",
                        Toast.LENGTH_SHORT).show()
                }
            },
            label = { Text("Agregado Familiar") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        NacionalidadeDropdownMenu(
            state = state,
            onNacionalidadeChange = viewModel::onNacionalidadeChange,
            isEditing = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.pedidos,
            onValueChange = viewModel::onPedidosChange,
            label = { Text("Pedidos") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (state.nome.isNotEmpty() && state.telemovel.isNotEmpty()
                    && state.nacionalidade.isNotEmpty() && state.agregadoFamiliar > 0
                    && state.pedidos.isNotEmpty()
                    && state.referencia.isNotEmpty()) {
                    viewModel.create(onSuccess = {
                        Toast.makeText(context, "Beneficiário criado com sucesso",
                            Toast.LENGTH_SHORT).show()
                        navController.popBackStack()},
                        onFailure = { errorMessage ->
                            Toast.makeText(context, errorMessage,
                        Toast.LENGTH_SHORT).show()})
                } else {
                    state.errorMessage = "Preencha todos os campos."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6)),
            enabled = !state.isLoading
        ) {
            Text(if (state.isLoading) "Carregando..." else "Registar Beneficiário")
        }

        if (state.errorMessage?.isNotEmpty() == true) {
            state.errorMessage?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun CreateBeneficiaryViewPreview() {
    DigiSocialTheme {
        CreateBeneficiaryView(navController = rememberNavController())
    }
}