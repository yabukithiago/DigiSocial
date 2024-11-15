package com.examples.digisocial

import com.examples.digisocial.ui.view.delete.DeleteBeneficiaryView
import com.examples.digisocial.ui.view.login.LoginViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.examples.digisocial.ui.theme.DigiSocialTheme
import com.examples.digisocial.ui.view.buttons.BeneficiaryButtonView
import com.examples.digisocial.ui.view.buttons.VoluntaryButtonView
import com.examples.digisocial.ui.view.edit.EditBeneficiaryView
import com.examples.digisocial.ui.view.home.HomePageAdminView
import com.examples.digisocial.ui.view.login.LoginView
import com.examples.digisocial.ui.view.register.RegisterBeneficiaryView
import com.examples.digisocial.ui.view.register.RegisterVoluntaryView
import com.examples.digisocial.ui.view.show.ShowBeneficiaryView
import com.examples.digisocial.ui.view.show.ShowVoluntaryView
import com.examples.digisocial.ui.view.user.UsersPageView
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val isLoading by remember { mutableStateOf(true) }
            val viewModel = LoginViewModel()
            DigiSocialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (isLoading) "loading" else "login",
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
                            Button(onClick = { viewModel.logout(
                                onLogoutSuccess = { navController.navigate("login") }) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                            ) {
                                Text("Logout")
                            }
                        }
                        composable("users") {
                            UsersPageView(navController)
                        }
                        //CRUD's
                        composable("registerVoluntary") {
                            RegisterVoluntaryView(navController)
                        }
                        composable("registerBeneficiary") {
                            RegisterBeneficiaryView(navController)
                        }
                        composable("readVoluntary") {
                            ShowVoluntaryView(navController)
                        }
                        composable("readBeneficiary"){
                            ShowBeneficiaryView(navController)
                        }
                        composable(
                            route = "editBeneficiary/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            EditBeneficiaryView(navController, id)
                        }
                        composable("editVoluntary"){
//                            EditVoluntaryView(navController)
                        }
                        composable("deleteBeneficiary/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            DeleteBeneficiaryView(navController = navController, id = id)
                        }
                        composable("deleteVoluntary"){
//                            DeleteVoluntaryView(navController)
                       }
                        composable("goToVoluntary") {
                            VoluntaryButtonView(navController)
                        }
                        composable("goToBeneficiary") {
                            BeneficiaryButtonView(navController)
                        }
                        composable("loading") {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    val auth = Firebase.auth
                    val currentUser = auth.currentUser
                    val firestore = Firebase.firestore

                    if (currentUser != null) {
                        val userId = currentUser.uid
                        firestore.collection("user").document(userId).get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val role = document.getString("role")
                                    when (role) {
                                        "admin" -> navController.navigate("homeAdmin")
                                        "voluntary" -> navController.navigate("homeVoluntary")
                                        "manager" -> navController.navigate("homeManager")
                                        else -> navController.navigate("login")
                                    }
                                } else {
                                    navController.navigate("login")
                                }
                            }
                            .addOnFailureListener {
                                navController.navigate("login")
                            }
                    } else {
                        navController.navigate("login")
                    }
                }
            }
        }
    }
}
