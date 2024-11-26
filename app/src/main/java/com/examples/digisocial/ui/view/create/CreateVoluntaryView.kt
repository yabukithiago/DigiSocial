package com.examples.digisocial.ui.view.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.R
import com.examples.digisocial.repository.VoluntaryRepository
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.ui.theme.DigiSocialTheme

@Composable
fun CreateVoluntaryView(navController: NavController) {
    val viewModel: CreateVoluntaryViewModel = viewModel()
    val state by viewModel.state
    var isPrivileged by remember { mutableStateOf(false) }

    TopBar(title = "Registar Voluntários", navController = navController)
    Box(modifier = Modifier.fillMaxSize())
    {
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
                label = { Text("Nome do Voluntário") },
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
                label = { Text("Telefone do Voluntário") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Email do Voluntário") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Senha do Voluntário") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                visualTransformation = PasswordVisualTransformation(),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Privilégios?",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(150.dp))

                Switch(
                    checked = isPrivileged,
                    onCheckedChange = { isPrivileged = it }
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(0.63f),
                onClick = {
                    if (state.email.isNotEmpty() && state.password.isNotEmpty()) {
                        VoluntaryRepository.createVoluntary(state.email, state.password,
                            state.nome, state.telefone, isPrivileged,
                            onSuccess = { navController.popBackStack() },
                            onFailure = { message -> state.errorMessage = message }
                        )
                    } else {
                        state.errorMessage = "Preencha todos os campos."
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                enabled = !state.isLoading
            ) {
                Text(if (state.isLoading) "Carregando..." else "Registar Voluntário")
            }

            if (state.errorMessage?.isNotEmpty() == true) {
                state.errorMessage?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun CreateVoluntaryViewPreview() {
    DigiSocialTheme {
        CreateVoluntaryView(navController = rememberNavController())
    }
}