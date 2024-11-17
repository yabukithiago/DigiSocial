package com.examples.digisocial.ui.view.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class RegisterBeneficiaryState(
    var nome: String = "",
    var telefone: String = "",
    var nacionalidade: String = "",
    var agregadoFamiliar: String = "",
    var numeroVisitas: Int = 0,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class RegisterBeneficiaryViewModel : ViewModel() {
    var state = mutableStateOf(RegisterBeneficiaryState())
        private set

    fun onNomeChange(newValue: String) {
        state.value = state.value.copy(nome = newValue)
    }

    fun onTelefoneChange(newValue: String) {
        state.value = state.value.copy(telefone = newValue)
    }

    fun onNacionalidadeChange(newValue: String) {
        state.value = state.value.copy(nacionalidade = newValue)
    }

    fun onAgregadoFamiliarChange(newValue: String) {
        state.value = state.value.copy(agregadoFamiliar = newValue)
    }
}