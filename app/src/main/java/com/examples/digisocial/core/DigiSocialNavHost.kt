package com.examples.digisocial.core

/* import com.examples.digisocial.ui.view.finance.FinanceDashboardView */
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.examples.digisocial.presentation.Screen
import com.examples.digisocial.presentation.beneficiary_list.BeneficiaryListScreen
import com.examples.digisocial.presentation.beneficiary_list.components.CreateBeneficiaryView
import com.examples.digisocial.presentation.beneficiary_list.components.EditBeneficiaryView
import com.examples.digisocial.presentation.home.HomePageAdminView
import com.examples.digisocial.presentation.home.HomePageJuntaView
import com.examples.digisocial.presentation.home.HomePageView
import com.examples.digisocial.presentation.home.HomePageVoluntary
import com.examples.digisocial.presentation.juntamember_list.JuntaMemberListScreen
import com.examples.digisocial.presentation.schedule_list.ScheduleListScreen
import com.examples.digisocial.presentation.transaction_list.TransactionListScreen
import com.examples.digisocial.presentation.user_list.UserListScreen
import com.examples.digisocial.presentation.visit_list.VisitListScreen
import com.examples.digisocial.presentation.voluntary_list.VoluntaryListScreen
import com.examples.digisocial.presentation.voluntaryschedule_list.VoluntaryScheduleListScreen
import com.examples.digisocial.ui.view.finance.CreateTransactionView
import com.examples.digisocial.ui.view.login.LoginView
import com.examples.digisocial.ui.view.register.RegisterView
import com.examples.digisocial.ui.view.report.ReportView
import com.examples.digisocial.ui.view.resetpassword.ResetPasswordView
import com.examples.digisocial.ui.view.schedule.CreateScheduleView
import com.examples.digisocial.ui.view.user.UsersPageView

@Composable
fun DigiSocialNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        //region Login
        composable(route = Screen.LoginScreen.route) {
            LoginView(navController, onLoginSuccess = {
                when (it) {
                    "admin" -> navController.navigate(Screen.HomePageAdminScreen.route)
                    "voluntary" -> navController.navigate(Screen.HomePageVoluntaryScreen.route)
                    "juntamember" -> navController.navigate(Screen.HomePageJuntaMemberScreen.route)
                    else -> navController.navigate(Screen.LoginScreen.route)
                }
            })
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterView(navController, onRegisterSuccess = { navController.navigate(Screen.LoginScreen.route) })
        }
        composable(route = Screen.ResetPasswordScreen.route) {
            ResetPasswordView(navController)
        }
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
            HomePageView()
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
        //endregion

        //region Beneficiary
        composable(route = Screen.CreateBeneficiaryScreen.route) {
            CreateBeneficiaryView(onDismiss = { }, onCreateBeneficiary = {})
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
        //endregion

        //region CRUD JuntaMember
        composable(route = Screen.JuntaMemberListScreen.route) {
            JuntaMemberListScreen(navController)
        }
        //endregion

        //region Transactions
        composable(Screen.CreateTransactionScreen.route) {
            CreateTransactionView(onDismiss = { }, onCreateTransaction = { })
        }
        composable(Screen.TransactionListScreen.route) {
            TransactionListScreen(navController)
        }
//        composable("showDashboard") {
//            FinanceDashboardView(navController)
//        }
        //endregion

        //region Schedule
        composable(Screen.CreateScheduleScreen.route) {
            CreateScheduleView(onDismiss = { }, onCreateSchedule = { })
        }
        composable(Screen.ScheduleListScreen.route) {
            ScheduleListScreen(navController = navController)
        }
        //endregion

        //region Schedule Voluntary

        composable(
            route = Screen.ScheduleDetailsScreen.route,
            arguments = listOf(navArgument("scheduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("scheduleId") ?: ""
            VoluntaryScheduleListScreen(navController, id)
        }
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