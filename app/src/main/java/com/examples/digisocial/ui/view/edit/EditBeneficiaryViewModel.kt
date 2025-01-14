package com.examples.digisocial.ui.view.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.BeneficiaryState
import com.examples.digisocial.data.repository.BeneficiaryRepository

data class EditBeneficiaryState(
    var nome: String = "",
    var telemovel: String = "",
    override var nacionalidade: String = "",
    var agregadoFamiliar: Long = 0,
    var numeroVisitas: Long = 0,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
) : BeneficiaryState

class EditBeneficiaryViewModel : ViewModel() {
    var state = mutableStateOf(EditBeneficiaryState())
        private set

    private val nome
        get() = state.value.nome
    private val telemovel
        get() = state.value.telemovel
    private val nacionalidade
        get() = state.value.nacionalidade
    private val agregadoFamiliar
        get() = state.value.agregadoFamiliar

    fun onNomeChange(newValue: String) {
        state.value = state.value.copy(nome = newValue)
    }

    fun onTelemovelChange(newValue: String) {
        state.value = state.value.copy(telemovel = newValue)
    }

    fun onNacionalidadeChange(newValue: String) {
        state.value = state.value.copy(nacionalidade = newValue)
    }

    fun onAgregadoFamiliarChange(newValue: Long) {
        state.value = state.value.copy(agregadoFamiliar = newValue)
    }

    fun update(id: String, onSuccess: () -> Unit){
        BeneficiaryRepository.updateBeneficiary(id = id, nome = nome,
            telemovel = telemovel, nacionalidade = nacionalidade,
            agregadoFamiliar = agregadoFamiliar,
            onSuccess = onSuccess, onFailure = { } )
    }
}