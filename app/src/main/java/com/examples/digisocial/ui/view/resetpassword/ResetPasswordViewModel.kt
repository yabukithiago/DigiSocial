package com.examples.digisocial.ui.view.resetpassword

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

data class ResetPasswordState(
    var email: String = "",
    var password: String = "",
    var isLoading: Boolean = false,
    var errorMessage: String? = null
)
class ResetPasswordViewModel : ViewModel() {
    var state = mutableStateOf(ResetPasswordState())
        private set

    fun onEmailChange(newValue: String) {
        state.value = state.value.copy(email = newValue)
    }

    fun sendPasswordResetEmail(email: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val auth = FirebaseAuth.getInstance()

        if(email.isEmpty()){
            state.value = state.value.copy(errorMessage = "Por favor, insira um email.")
            return
        }

        state.value = state.value.copy(isLoading = true)

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    Log.d(TAG, "Email enviado.")
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Erro ao enviar email")
                }
            }
    }
}