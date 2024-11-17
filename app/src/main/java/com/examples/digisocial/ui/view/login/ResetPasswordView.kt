package com.examples.digisocial.ui.view.login

import LoginViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

@Composable
fun ResetPasswordView(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    val state by viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo DigiSocial"
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                    viewModel.sendPasswordResetEmail(state.email,
                        onSuccess = {
                            navController.navigate("login")
                        },
                        onFailure = { message ->
                            state.errorMessage = message
                        }
                    )
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.8f),
            enabled = state.email.isNotEmpty() && !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(text = "Redefinir senha")
            }
        }

        TextButton(onClick = { navController.navigate("login") }) {
            Text(
                text = "Voltar para o Login"
            )
        }

        if (state.errorMessage != null) {
            Text(
                text = state.errorMessage ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResetPasswordView() {
    ResetPasswordView(navController = rememberNavController())
}
