package com.examples.digisocial.presentation

sealed class Screen(val route: String) {
    data object LoginScreen: Screen("login_screen")

    //region HomePages
    data object HomePageAdminScreen: Screen("home_page_admin_screen")
    data object HomePageVoluntaryScreen: Screen("home_page_user_screen")
    data object HomePageJuntaMemberScreen: Screen("home_page_junta_member_screen")
    data object HomePageScreen: Screen("home_page_screen")
    //endregion

    //region User
    data object UserPageScreen: Screen("user_page_screen")
    data object UserListScreen: Screen("user_list_screen")

    //endregion

    //region Beneficiary
    data object BeneficiaryListScreen: Screen("beneficiary_list_screen")
    data object CreateBeneficiaryScreen: Screen("create_beneficiary_screen")
    data object EditBeneficiaryScreen: Screen("edit_beneficiary_screen/{beneficiaryId}")
    data object DeleteBeneficiaryScreen: Screen("delete_beneficiary_screen/{beneficiaryId}")
    data object BeneficiaryDetailsScreen: Screen("beneficiary_details_screen/{beneficiaryId}"){
        fun createRoute(beneficiaryId: String) = "beneficiary_details_screen/$beneficiaryId"
    }

    //endregion

    //region Voluntary
    data object VoluntaryListScreen: Screen("voluntary_list_screen")
    data object CreateVoluntaryScreen: Screen("create_voluntary_screen")
    data object EditVoluntaryScreen: Screen("edit_voluntary_screen/{voluntaryId}")
    data object DeleteVoluntaryScreen: Screen("delete_voluntary_screen/{voluntaryId}")

    //endregion

    //region JuntaMember
    data object JuntaMemberListScreen: Screen("junta_member_list_screen")
    //endregion
}