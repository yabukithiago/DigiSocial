package com.examples.digisocial

import com.examples.digisocial.ui.view.register.RegisterVolunteerView
import com.examples.digisocial.ui.view.login.LoginView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.theme.DigiSocialTheme
import com.examples.digisocial.ui.view.home.HomePageAdminView
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
                            LoginView(onLoginSuccess = { role ->
                                when (role) {
                                    "admin" -> navController.navigate("homeAdmin")
                                    "voluntary" -> navController.navigate("homeVoluntary")
                                    "manager" -> navController.navigate("homeManager")
                                    else -> navController.navigate("login")
                                }
                            })
                        }
                        composable(route = "homeAdmin"){
                            HomePageAdminView(navController = navController)
                        }
                        composable(route = "homeVoluntary"){
                            Text("Home Voluntary")
                        }
                        composable(route = "homeManager"){
                            Text("Home Manager")
                        }
                        composable(route = "registerVolunteer"){
                            RegisterVolunteerView(navController = navController)
                        }
                    }
                }
            }
        }
    }
}