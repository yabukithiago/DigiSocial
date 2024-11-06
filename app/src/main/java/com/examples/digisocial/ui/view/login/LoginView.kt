package com.examples.digisocial.ui.view.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.examples.digisocial.R
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.tooling.preview.Preview
import com.examples.digisocial.ui.theme.DigiSocialTheme

@Composable
fun LoginView(modifier: Modifier = Modifier, onLoginSuccess: () -> Unit) {
    val viewModel by remember { mutableStateOf(LoginViewModel()) }
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
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.login(onLoginSuccess = onLoginSuccess)
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            enabled = !state.isLoading
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = state.errorMessage ?: "")
        if (state.isLoading)
            CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    DigiSocialTheme {
        LoginView(modifier = Modifier.fillMaxSize()) {}
    }
}
