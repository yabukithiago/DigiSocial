package com.examples.digisocial.view

import com.examples.digisocial.ui.view.register.RegisterVolunteerView
import com.examples.digisocial.ui.view.login.LoginView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.theme.DigiSocialTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            DigiSocialTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)){
                        composable(route = "login"){
                            LoginView(onLoginSuccess = {
                                navController.navigate("home")
                            })
                        }
                        composable(route = "home"){
                            RegisterVolunteerView()
                            /*HomeView()*/
                        }
                        composable(route = "registerVolunteer"){
                            RegisterVolunteerView()
                        }
                    }
                }
            }
        }
    }
}