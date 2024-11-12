package com.examples.digisocial

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.theme.DigiSocialTheme
import com.examples.digisocial.ui.view.buttons.VoluntaryButtonView
import com.examples.digisocial.ui.view.home.HomePageAdminView
import com.examples.digisocial.ui.view.login.LoginView
import com.examples.digisocial.ui.view.register.RegisterVolunteerView
import com.examples.digisocial.ui.view.user.UsersPageView

@Composable
fun DigiSocialApp() {
    DigiSocialTheme {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "login",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("login") {
                    LoginView(onLoginSuccess = { role ->
                        val destination = when (role) {
                            "admin" -> "homeAdmin"
                            "voluntary" -> "homeVoluntary"
                            "manager" -> "homeManager"
                            else -> "login"
                        }
                        navController.navigate(destination)
                    })
                }
                //Homes
                composable("homeAdmin") {
                    HomePageAdminView(navController)
                }
                composable("homeVoluntary") {
                    Text("Home Voluntary")
                }
                composable("homeManager") {
                    Text("Home Manager")
                }
                composable("users") {
                    UsersPageView(navController)
                }
                composable("registerVolunteer") {
                    RegisterVolunteerView(navController)
                }
                composable("goToVoluntary") {
                    VoluntaryButtonView(navController)
                }
            }
        }
    }
}
