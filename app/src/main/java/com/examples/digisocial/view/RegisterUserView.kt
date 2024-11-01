package com.examples.digisocial.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun RegisterUserView(onUserRegistered: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val auth: FirebaseAuth = Firebase.auth

    fun registerUser(email: String, password: String) {
        isLoading = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    // dados adicionais, como o nome, se utilizarmos base de dados
                    onUserRegistered()
                } else {
                    errorMessage = "Erro ao registrar usuário: ${task.exception?.message}"
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                    registerUser(email, password)
                } else {
                    errorMessage = "Preencha todos os campos."
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Registrando..." else "Registrar Usuário")
        }
    }
}
