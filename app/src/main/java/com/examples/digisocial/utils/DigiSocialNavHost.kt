package com.examples.digisocial.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.examples.digisocial.ui.view.buttons.BeneficiaryButtonView
import com.examples.digisocial.ui.view.buttons.VoluntaryButtonView
import com.examples.digisocial.ui.view.delete.DeleteBeneficiaryView
import com.examples.digisocial.ui.view.edit.EditBeneficiaryView
import com.examples.digisocial.ui.view.home.HomePageAdminView
import com.examples.digisocial.ui.view.home.HomePageVoluntario
import com.examples.digisocial.ui.view.login.LoginView
import com.examples.digisocial.ui.view.login.ResetPasswordView
import com.examples.digisocial.ui.view.register.RegisterBeneficiaryView
import com.examples.digisocial.ui.view.register.RegisterVoluntaryView
import com.examples.digisocial.ui.view.show.ShowBeneficiaryView
import com.examples.digisocial.ui.view.show.ShowVoluntaryView
import com.examples.digisocial.ui.view.user.UsersPageView

@Composable
fun DigiSocialNavHost(navController: NavHostController, isLoading: Boolean) {
        NavHost(
            navController = navController,
            startDestination = if (isLoading) "loading" else "login"
        ) {
            composable("login") {
                LoginView(navController, onLoginSuccess = { role ->
                    val destination = when (role) {
                        "admin" -> "homeAdmin"
                        "voluntary" -> "homeVoluntary"
                        "manager" -> "homeManager"
                        else -> "login"
                    }
                    navController.navigate(destination)
                })
            }
            composable("resetPassword") {
                ResetPasswordView(navController)
            }
            //Homes
            composable("homeAdmin") {
                HomePageAdminView(navController)
            }
            composable("homeVoluntary") {

                HomePageVoluntario(navController)
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
            composable("readBeneficiary") {
                ShowBeneficiaryView(navController)
            }
            composable(
                route = "editBeneficiary/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                EditBeneficiaryView(navController, id)
            }
            composable("editVoluntary") {
//                            EditVoluntaryView(navController)
            }
            composable("deleteBeneficiary/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                DeleteBeneficiaryView(navController = navController, id = id)
            }
            composable("deleteVoluntary") {
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