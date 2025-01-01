package com.examples.digisocial.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.theme.DigiSocialTheme

@Composable
fun DigiSocialApp() {
    val navController = rememberNavController()
    val isLoading by remember { mutableStateOf(true) }
    DigiSocialTheme {
            DigiSocialNavHost(
                navController = navController,
                isLoading = isLoading
            )
        HandleUserAuthentication(navController)
    }

}
