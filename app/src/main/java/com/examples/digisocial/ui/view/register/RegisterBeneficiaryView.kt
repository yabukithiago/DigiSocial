package com.examples.digisocial.ui.view.register

import NacionalidadeDropdownMenu
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.R
import com.examples.digisocial.ui.components.TopBar
import com.examples.digisocial.ui.theme.DigiSocialTheme

@Composable
fun RegisterBeneficiaryView(navController: NavController) {
    val viewModel: RegisterBeneficiaryViewModel = viewModel()
    val state by viewModel.state

    TopBar(title = "Registar Beneficiários", navController = navController)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(150.dp),
            painter = painterResource(id = R.drawable.baseline_person_add_24),
            contentDescription = "User Icon"
        )

        TextField(
            value = state.nome,
            onValueChange = viewModel::onNomeChange,
            label = { Text("Nome do Beneficiário") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.telefone,
            onValueChange = viewModel::onTelefoneChange,
            label = { Text("Telefone do Beneficiário") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

//        NacionalidadeDropdownMenu(
//            state = state,
//            onNacionalidadeChange = viewModel::onNacionalidadeChange
//        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.agregadoFamiliar,
            onValueChange = viewModel::onAgregadoFamiliarChange,
            label = { Text("Agregado Familiar do Beneficiário") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (state.nome.isNotEmpty() && state.telefone.isNotEmpty()
                    && state.nacionalidade.isNotEmpty() && state.agregadoFamiliar.isNotEmpty()) {
                    viewModel.registerBeneficiary(
                        state.nome, state.telefone,
                        state.nacionalidade, state.agregadoFamiliar,
                        onSuccess = {  },
                        onFailure = { message -> state.errorMessage = message }
                    )
                } else {
                    state.errorMessage = "Preencha todos os campos."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
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
fun RegisterBeneficiaryViewPreview() {
    DigiSocialTheme {
        RegisterBeneficiaryView(navController = rememberNavController())
    }
}