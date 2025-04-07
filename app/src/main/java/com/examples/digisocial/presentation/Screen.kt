package com.examples.digisocial.presentation

sealed class Screen(val route: String) {
    data object LoginScreen: Screen("login_screen")
    data object HomePageAdminScreen: Screen("home_page_admin_screen")
    data object HomePageVoluntaryScreen: Screen("home_page_user_screen")
    data object HomePageJuntaMemberScreen: Screen("home_page_junta_member_screen")
    data object HomePageScreen: Screen("home_page_screen")
    data object BeneficiaryListScreen: Screen("beneficiary_list_screen")
    data object CreateBeneficiaryScreen: Screen("create_beneficiary_screen")
    data object EditBeneficiaryScreen: Screen("edit_beneficiary_screen/{beneficiaryId}")
    data object DeleteBeneficiaryScreen: Screen("delete_beneficiary_screen/{beneficiaryId}")
    data object BeneficiaryDetailsScreen: Screen("beneficiary_details_screen/{beneficiaryId}"){
        fun createRoute(beneficiaryId: String) = "beneficiary_details_screen/$beneficiaryId"
    }
}