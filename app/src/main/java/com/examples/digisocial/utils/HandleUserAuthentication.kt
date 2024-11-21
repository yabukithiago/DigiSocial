package com.examples.digisocial.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
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
                            "admin" -> navController.navigate("homeAdmin")
                            "voluntary" -> navController.navigate("homeVoluntary")
                            //"manager" -> navController.navigate("homeManager")
                            "juntamember" -> navController.navigate("homeJuntaMember")
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