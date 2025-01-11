package com.examples.digisocial.ui.view.home

import com.examples.digisocial.ui.view.login.LoginViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.theme.DigiSocialTheme

@Composable
fun HomePageView(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Não possui acesso, por favor entre em contato com o administrador.",
            textAlign = Center,
            softWrap = true,
            overflow = TextOverflow.Ellipsis)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.logout(onLogoutSuccess = { navController.navigate("login") }) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF044AA6))
        ) {
            Text("Voltar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomePageView() {
    DigiSocialTheme {
        HomePageView(navController = rememberNavController())
    }
}