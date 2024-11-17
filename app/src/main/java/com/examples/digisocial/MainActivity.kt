package com.examples.digisocial

import com.examples.digisocial.utils.DigiSocialApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.examples.digisocial.ui.theme.DigiSocialTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            DigiSocialTheme {
                DigiSocialApp()
            }
        }
    }
}
