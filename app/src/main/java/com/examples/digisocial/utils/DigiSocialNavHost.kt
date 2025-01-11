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
import com.examples.digisocial.ui.view.create.CreateBeneficiaryView
import com.examples.digisocial.ui.view.delete.DeleteBeneficiaryView
import com.examples.digisocial.ui.view.delete.DeleteJuntaMemberView
import com.examples.digisocial.ui.view.delete.DeleteUserView
import com.examples.digisocial.ui.view.delete.DeleteVoluntaryView
import com.examples.digisocial.ui.view.edit.EditBeneficiaryView
import com.examples.digisocial.ui.view.edit.EditUserView
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
import com.examples.digisocial.ui.view.schedulevoluntary.RegisterVoluntaryScheduleView
import com.examples.digisocial.ui.view.schedule.ShowScheduleView
import com.examples.digisocial.ui.view.schedulevoluntary.DeleteVoluntaryScheduleView
import com.examples.digisocial.ui.view.schedulevoluntary.ShowVoluntaryScheduleView
import com.examples.digisocial.ui.view.show.ShowBeneficiaryView
import com.examples.digisocial.ui.view.show.ShowJuntaMemberView
import com.examples.digisocial.ui.view.show.ShowVoluntaryView
import com.examples.digisocial.ui.view.user.PendingUserView
import com.examples.digisocial.ui.view.user.UsersPageView
import com.examples.digisocial.ui.view.visit.ShowAttendanceView
import com.examples.digisocial.ui.view.visit.VisitRegisterView

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
        composable("readPendingUser") {
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
            HomePageVoluntary(navController)
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
        composable("deleteVoluntary/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DeleteVoluntaryView(navController = navController, id = id)
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
        composable("deleteJuntaMember/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DeleteJuntaMemberView(navController = navController, id = id)
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
        composable("addVoluntaryOnSchedule/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            RegisterVoluntaryScheduleView(navController, id = id)
        }
        composable("deleteVoluntaryOnSchedule/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DeleteVoluntaryScheduleView(navController, id = id)
        }
        composable("showVoluntaryOnSchedule/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            ShowVoluntaryScheduleView(navController, id = id)
        }
        //endregion

        //region Attendance
        composable("attendanceRegister/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            VisitRegisterView(navController = navController, id = id)
        }
        composable("showAttendance/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            ShowAttendanceView(navController, beneficiaryId = id)
        }
        //endregion

        //region Reports
        composable("reports"){
            ReportView(navController)
        }
        //endregion

        //region Loading
        composable("loading") {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        //endregion
    }
}