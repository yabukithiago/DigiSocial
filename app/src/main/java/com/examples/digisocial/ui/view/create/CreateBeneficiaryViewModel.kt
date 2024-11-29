package com.examples.digisocial.ui.view.create

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.BeneficiaryState
import com.examples.digisocial.data.models.Visit
import com.examples.digisocial.data.repository.BeneficiaryRepository

data class CreateBeneficiaryState(
    var nome: String = "",
    var telefone: String = "",
    override var nacionalidade: String = "",
    var editedNacionalidade: String = "",
    var agregadoFamiliar: String = "",
    var numeroVisitas: Int = 0,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
) : BeneficiaryState

class CreateBeneficiaryViewModel : ViewModel() {
    var state = mutableStateOf(CreateBeneficiaryState())
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

    fun create(onSuccess: () -> Unit){
        BeneficiaryRepository.createBeneficiary(nome = nome,
            telefone = telefone, nacionalidade = nacionalidade, agregadoFamiliar = agregadoFamiliar,
            onSuccess = onSuccess, onFailure = { } )
    }
}