package com.examples.digisocial.ui.view.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.BeneficiaryState
import com.examples.digisocial.data.repository.BeneficiaryRepository

data class DeleteBeneficiaryState(
    var nome: String = "",
    var telefone: String = "",
    override var nacionalidade: String = "",
    var agregadoFamiliar: String = "",
    var numeroVisitas: Int = 0,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
) : BeneficiaryState

class DeleteBeneficiaryViewModel : ViewModel() {
    var state = mutableStateOf(DeleteBeneficiaryState())
        private set

    fun deleteBeneficiary(id: String, onSuccess: () -> Unit){
        BeneficiaryRepository.deleteBeneficiary(id = id, onSuccess = onSuccess, onFailure = { } )
    }
}