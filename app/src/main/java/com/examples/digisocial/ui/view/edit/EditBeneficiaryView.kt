package com.examples.digisocial.ui.view.edit

import com.examples.digisocial.ui.components.NacionalidadeDropdownMenu
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
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
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun EditBeneficiaryView(navController: NavController, id: String) {
    val viewModel: EditBeneficiaryViewModel = viewModel()
    val state by viewModel.state
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    LaunchedEffect(id) {
        db.collection("beneficiary").document(id).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    state.nome = document.getString("nome") ?: ""
                    state.telemovel = document.getString("telemovel") ?: ""
                    state.nacionalidade = document.getString("nacionalidade") ?: ""
                    state.agregadoFamiliar = document.getLong("agregadoFamiliar") ?: 0
                    state.numeroVisitas = document.getLong("numeroVisitas") ?: 0
                }
            }
            .addOnFailureListener { e ->
                Log.e("EditBeneficiary", "Erro ao carregar beneficiário: ${e.message}")
            }
    }

    TopBar(title = "Editar Beneficiários", navController = navController)

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
            painter = painterResource(id = R.drawable.baseline_edit_24),
            contentDescription = "Edit Icon"
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
            label = { Text("Telefone") },
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
            isEditing = true
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

        Button(
            onClick = {
                if (state.nome.isNotEmpty() && state.telemovel.isNotEmpty()
                    && state.nacionalidade.isNotEmpty() && state.agregadoFamiliar > 0) {
                    viewModel.update(id, onSuccess = {
                        Toast.makeText(context, "Beneficiário editado com sucesso", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()})
                } else {
                    state.errorMessage = "Preencha todos os campos."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6)),
            enabled = !state.isLoading
        ) {
            Text(if (state.isLoading) "Carregando..." else "Editar Beneficiário")
        }

        if (state.errorMessage?.isNotEmpty() == true) {
            state.errorMessage?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun EditBeneficiaryViewPreview() {
    DigiSocialTheme {
        EditBeneficiaryView(
            navController = rememberNavController(),
            id = "123"
        )
    }
}