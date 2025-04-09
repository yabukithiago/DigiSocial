package com.examples.digisocial.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.examples.digisocial.presentation.Screen
import com.examples.digisocial.presentation.beneficiary_list.BeneficiaryListScreen
import com.examples.digisocial.presentation.juntamember_list.JuntaMemberListScreen
import com.examples.digisocial.presentation.user_list.UserListScreen
import com.examples.digisocial.ui.view.create.CreateBeneficiaryView
import com.examples.digisocial.ui.view.delete.DeleteBeneficiaryView
import com.examples.digisocial.ui.view.delete.DeleteVoluntaryView
import com.examples.digisocial.ui.view.edit.EditBeneficiaryView
import com.examples.digisocial.ui.view.finance.AddTransactionView
import com.examples.digisocial.ui.view.finance.FinanceDashboardView
import com.examples.digisocial.ui.view.finance.ShowTransactionView
import com.examples.digisocial.ui.view.home.HomePageAdminView
import com.examples.digisocial.ui.view.home.HomePageJuntaView
import com.examples.digisocial.ui.view.home.HomePageView
import com.examples.digisocial.ui.view.home.HomePageVoluntary
import com.examples.digisocial.ui.view.login.LoginView
import com.examples.digisocial.ui.view.register.RegisterView
import com.examples.digisocial.ui.view.report.ReportView
import com.examples.digisocial.ui.view.resetpassword.ResetPasswordView
import com.examples.digisocial.ui.view.schedule.CreateScheduleView
import com.examples.digisocial.ui.view.schedule.DeleteScheduleView
import com.examples.digisocial.ui.view.schedule.ShowScheduleView
import com.examples.digisocial.ui.view.user.UsersPageView
import com.examples.digisocial.presentation.visit_list.VisitListScreen
import com.examples.digisocial.presentation.voluntary_list.VoluntaryListScreen

@Composable
fun DigiSocialNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        //region Login
        composable(route = Screen.LoginScreen.route) {
            LoginView(navController, onLoginSuccess = { role ->
                val destination = when (role) {
                    "admin" -> Screen.HomePageAdminScreen.route
                    "voluntary" -> Screen.HomePageVoluntaryScreen.route
                    "juntamember" -> Screen.HomePageJuntaMemberScreen.route
                    "" -> Screen.HomePageScreen.route
                    else -> "login"
                }
                navController.navigate(destination)
            })
        }
        composable("logout") {
            navController.navigate("login")
        }
        composable("register") {
            RegisterView(navController, onRegisterSuccess = { navController.navigate("login") })
        }
        composable("resetPassword") {
            ResetPasswordView(navController)
        }
        //endregion

        //region User
//        composable("readPendingUser") {
//            PendingUserView(navController)
//        }
//        composable(
//            route = "editUser/{id}",
//            arguments = listOf(navArgument("id") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val id = backStackEntry.arguments?.getString("id") ?: ""
//            EditUserView(navController, id)
//        }
//        composable("deleteUser/{id}") { backStackEntry ->
//            val id = backStackEntry.arguments?.getString("id") ?: ""
//            DeleteUserView(navController = navController, id = id)
//        }
        //endregion

        //region Homes
        composable(route = Screen.HomePageAdminScreen.route) {
            HomePageAdminView(navController)
        }
        composable(route = Screen.HomePageVoluntaryScreen.route) {
            HomePageVoluntary(navController)
        }
        composable(route = Screen.HomePageJuntaMemberScreen.route) {
            HomePageJuntaView(navController)
        }
        composable(route = Screen.HomePageScreen.route) {
            HomePageView(navController)
        }
        //endregion

        //region User
        composable(route = Screen.UserPageScreen.route) {
            UsersPageView(navController)
        }
        composable(route = Screen.UserListScreen.route){
            UserListScreen(navController)
        }
        //endregion

        //region Voluntary
        composable(route = Screen.VoluntaryListScreen.route) {
            VoluntaryListScreen(navController)
        }
        composable(
            route = Screen.DeleteVoluntaryScreen.route,
            arguments = listOf(navArgument("voluntaryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DeleteVoluntaryView(navController = navController, id = id)
        }
        //endregion

        //region Beneficiary
        composable(route = Screen.CreateBeneficiaryScreen.route) {
            CreateBeneficiaryView(navController, onCreateBeneficiary = {})
        }
        composable(route = Screen.BeneficiaryListScreen.route) {
            BeneficiaryListScreen(navController)
        }
        composable(
            route = Screen.EditBeneficiaryScreen.route,
            arguments = listOf(navArgument("beneficiaryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("beneficiaryId") ?: ""
            EditBeneficiaryView(id, onDismiss = { }, onEditBeneficiary = { })
        }
        composable(
            route = Screen.DeleteBeneficiaryScreen.route,
            arguments = listOf(navArgument("beneficiaryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DeleteBeneficiaryView(navController = navController, id = id)
        }
        //endregion

        //region CRUD JuntaMember
        composable(route = Screen.JuntaMemberListScreen.route) {
            JuntaMemberListScreen(navController)
        }
        //endregion

        //region Transactions
        composable("addNewTransaction") {
            AddTransactionView(navController)
        }
        composable("showTransaction") {
            ShowTransactionView(navController)
        }
        composable("showDashboard") {
            FinanceDashboardView(navController)
        }
        //endregion

        //region Schedule
        composable("createSchedule") {
            CreateScheduleView(navController = navController)
        }
        composable("readSchedule") {
            ShowScheduleView(navController = navController)
        }
        composable("deleteSchedule/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DeleteScheduleView(navController = navController, id = id)
        }
        //endregion

        //region Schedule Voluntary
//        composable("addVoluntaryOnSchedule/{id}") { backStackEntry ->
//            val id = backStackEntry.arguments?.getString("id") ?: ""
//            RegisterVoluntaryScheduleView(navController, id = id)
//        }
//        composable("deleteVoluntaryOnSchedule/{id}") { backStackEntry ->
//            val id = backStackEntry.arguments?.getString("id") ?: ""
//            DeleteVoluntaryScheduleView(navController, id = id)
//        }
//        composable("showVoluntaryOnSchedule/{id}") { backStackEntry ->
//            val id = backStackEntry.arguments?.getString("id") ?: ""
//            ShowVoluntaryScheduleView(navController, id = id)
//        }
        //endregion

        //region Attendance

        composable(
            route = Screen.BeneficiaryDetailsScreen.route,
            arguments = listOf(navArgument("beneficiaryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("beneficiaryId") ?: ""
            VisitListScreen(navController, id)
        }
        //endregion

        //region Reports
        composable("reports") {
            ReportView(navController)
        }
        //endregion
    }

    HandleUserAuthentication(navController)
}