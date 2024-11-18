package com.examples.digisocial.ui.view.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class RegisterJuntaMemberState(
    var nome: String = "",
    var telefone: String = "",
    var email: String = "",
    var password: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)
class RegisterJuntaMemberViewModel : ViewModel() {
    var state = mutableStateOf(RegisterJuntaMemberState())
        private set

    fun onEmailChange(newValue: String) {
        state.value = state.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        state.value = state.value.copy(password = newValue)
    }

    fun onNomeChange(newValue: String) {
        state.value = state.value.copy(nome = newValue)
    }

    fun onTelefoneChange(newValue: String) {
        state.value = state.value.copy(telefone = newValue)
    }
}
