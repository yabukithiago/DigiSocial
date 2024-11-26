package com.examples.digisocial.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.examples.digisocial.ui.view.delete.DeleteBeneficiaryView
import com.examples.digisocial.ui.view.edit.EditBeneficiaryView
import com.examples.digisocial.ui.view.finance.AddTransactionView
import com.examples.digisocial.ui.view.finance.ShowTransactionView
import com.examples.digisocial.ui.view.home.HomePageAdminView
import com.examples.digisocial.ui.view.home.HomePageVoluntario
import com.examples.digisocial.ui.view.home.HomePageJuntaView
import com.examples.digisocial.ui.view.login.LoginView
import com.examples.digisocial.ui.view.resetpassword.ResetPasswordView
import com.examples.digisocial.ui.view.create.CreateBeneficiaryView
import com.examples.digisocial.ui.view.delete.DeleteUserView
import com.examples.digisocial.ui.view.edit.EditUserView
import com.examples.digisocial.ui.view.home.HomePageView
import com.examples.digisocial.ui.view.register.RegisterView
import com.examples.digisocial.ui.view.show.ShowBeneficiaryView
import com.examples.digisocial.ui.view.show.ShowJuntaMemberView
import com.examples.digisocial.ui.view.show.ShowVoluntaryView
import com.examples.digisocial.ui.view.user.PendingUserView
import com.examples.digisocial.ui.view.user.UsersPageView

@Composable
fun DigiSocialNavHost(navController: NavHostController, isLoading: Boolean) {
        NavHost(
            navController = navController,
            startDestination = if (isLoading) "loading" else "login"
        ) {
            //region Login
            composable("login") {
                LoginView(navController, onLoginSuccess = { role ->
                    val destination = when (role) {
                        "admin" -> "homeAdmin"
                        "voluntary" -> "homeVoluntary"
                        "manager" -> "homeManager"
                        "juntamember" -> "homeJuntaMember"
                        "" -> "homePage"
                        else -> "login"
                    }
                    navController.navigate(destination)
                })
            }
            composable("register"){
                RegisterView(navController, onRegisterSuccess = { navController.navigate("login") })
            }
            composable("resetPassword") {
                ResetPasswordView(navController)
            }
            //endregion
            //region User
            composable("readPendingUser"){
                PendingUserView(navController)
            }
            composable(
                route = "editUser/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                EditUserView(navController, id)
            }
            composable("deleteUser/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                DeleteUserView(navController = navController, id = id)
            }
            //endregion
            //region Homes
            composable("homeAdmin") {
                HomePageAdminView(navController)
            }
            composable("homeVoluntary") {

                HomePageVoluntario(navController)
            }
            composable("homeJuntaMember") {
                HomePageJuntaView(navController)
            }
            composable("homePage") {
                HomePageView(navController)
            }
            composable("users") {
                UsersPageView(navController)
            }
            //endregion

            //region CRUD Voluntary
            composable("readVoluntary") {
                ShowVoluntaryView(navController)
            }
            composable("editVoluntary") {
//                            EditVoluntaryView(navController)
            }

            composable("deleteVoluntary") {
//                            DeleteVoluntaryView(navController)
            }
            //endregion

            //region CRUD Beneficiary
            composable("createBeneficiary") {
                CreateBeneficiaryView(navController)
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
            composable("deleteBeneficiary/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                DeleteBeneficiaryView(navController = navController, id = id)
            }
            //endregion

            //region CRUD JuntaMember
            composable("readJuntaMember") {
                ShowJuntaMemberView(navController)
            }
//            composable(
//                route = "editJuntaMember/{id}",
//                arguments = listOf(navArgument("id") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val id = backStackEntry.arguments?.getString("id") ?: ""
//                EditJuntaMemberView(navController, id)
//            }
//            composable("deleteJuntaMember/{id}") { backStackEntry ->
//                val id = backStackEntry.arguments?.getString("id") ?: ""
//                DeleteJuntaMemberView(navController = navController, id = id)
//            }
            //endregion

            //region Transactions
            composable("addNewTransaction"){
                AddTransactionView(navController)
            }
            composable("showTransaction"){
                ShowTransactionView(navController)
            }
            //endregion

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
