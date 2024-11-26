package com.examples.digisocial.ui.view.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.BeneficiaryState
import com.examples.digisocial.data.repository.BeneficiaryRepository

data class EditBeneficiaryState(
    var nome: String = "",
    var telefone: String = "",
    override var nacionalidade: String = "",
    var agregadoFamiliar: String = "",
    var numeroVisitas: Int = 0,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
) : BeneficiaryState

class EditBeneficiaryViewModel : ViewModel() {
    var state = mutableStateOf(EditBeneficiaryState())
        private set

    private val nome
        get() = state.value.nome
    private val telefone
        get() = state.value.telefone
    private val nacionalidade
        get() = state.value.nacionalidade
    private val agregadoFamiliar
        get() = state.value.agregadoFamiliar

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

    fun update(id: String, onSuccess: () -> Unit){
        BeneficiaryRepository.updateBeneficiary(id = id, nome = nome,
            telefone = telefone, nacionalidade = nacionalidade,
            agregadoFamiliar = agregadoFamiliar, numeroVisitas = 0,
            onSuccess = onSuccess, onFailure = { } )
    }
}