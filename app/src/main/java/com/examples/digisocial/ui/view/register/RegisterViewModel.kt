package com.examples.digisocial.ui.view.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class RegisterViewModel : ViewModel() {
    var state = mutableStateOf(RegisterState())
        private set
    private val auth = FirebaseAuth.getInstance()

    fun onNameChange(newValue: String) {
        state.value = state.value.copy(name = newValue)
    }

    fun onEmailChange(email: String) {
        state.value = state.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        state.value = state.value.copy(password = password)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        state.value = state.value.copy(confirmPassword = confirmPassword)
    }

    fun register(onRegisterSuccess: () -> Unit, onRegisterFailure: (String) -> Unit) {
        state.value = state.value.copy(isLoading = true)

        if (state.value.password != state.value.confirmPassword) {
            onRegisterFailure("As senhas não coincidem")
            state.value = state.value.copy(isLoading = false)
            return
        }

        auth.createUserWithEmailAndPassword(state.value.email, state.value.password)
            .addOnCompleteListener { task ->
                state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    onRegisterSuccess()
                } else {
                    val errorMessage = task.exception?.localizedMessage ?: "Erro desconhecido"
                    onRegisterFailure(errorMessage)
                }
            }
    }
}
