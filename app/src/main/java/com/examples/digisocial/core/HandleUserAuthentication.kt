package com.examples.digisocial.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.examples.digisocial.presentation.Screen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun HandleUserAuthentication(navController: NavController) {
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
                            "admin" -> navController.navigate(Screen.HomePageAdminScreen.route)
                            "voluntary" -> navController.navigate(Screen.HomePageVoluntaryScreen.route)
                            "juntamember" -> navController.navigate(Screen.HomePageJuntaMemberScreen.route)
                            else -> navController.navigate(Screen.LoginScreen.route)
                        }
                    } else {
                        navController.navigate(Screen.LoginScreen.route)
                    }
                }
                .addOnFailureListener {
                    navController.navigate(Screen.LoginScreen.route)
                }
        } else {
            navController.navigate(Screen.LoginScreen.route)
        }
    }
}