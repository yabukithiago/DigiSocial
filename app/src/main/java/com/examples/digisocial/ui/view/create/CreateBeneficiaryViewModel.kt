package com.examples.digisocial.ui.view.create

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.BeneficiaryState
import com.examples.digisocial.data.repository.BeneficiaryRepository

data class CreateBeneficiaryState(
    var nome: String = "",
    var telemovel: String = "",
    var referencia: String = "",
    var agregadoFamiliar: Long = 0,
    override var nacionalidade: String = "",
    var pedidos: String = "",
    var numeroVisitas: Long = 0,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
) : BeneficiaryState

class CreateBeneficiaryViewModel : ViewModel() {
    var state = mutableStateOf(CreateBeneficiaryState())
        private set

    private val nome
        get() = state.value.nome
    private val telemovel
        get() = state.value.telemovel
    private val referencia
        get() = state.value.referencia
    private val agregadoFamiliar
        get() = state.value.agregadoFamiliar
    private val nacionalidade
        get() = state.value.nacionalidade
    private val pedidos
        get() = state.value.pedidos


    fun onNomeChange(newValue: String) {
        state.value = state.value.copy(nome = newValue)
    }

    fun onTelemovelChange(newValue: String) {
        state.value = state.value.copy(telemovel = newValue)
    }

    fun onReferenciaChange(newValue: String) {
        state.value = state.value.copy(referencia = newValue)
    }

    fun onAgregadoFamiliarChange(newValue: Long) {
        state.value = state.value.copy(agregadoFamiliar = newValue)
    }

    fun onNacionalidadeChange(newValue: String) {
        state.value = state.value.copy(nacionalidade = newValue)
    }

    fun onPedidosChange(newValue: String){
        state.value = state.value.copy(pedidos = newValue)
    }

    fun create(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        BeneficiaryRepository.createBeneficiary(nome = nome,
            telemovel = telemovel, referencia = referencia, agregadoFamiliar = agregadoFamiliar, nacionalidade = nacionalidade,
            pedidos = pedidos,
            onSuccess = onSuccess, onFailure = onFailure)
    }
}