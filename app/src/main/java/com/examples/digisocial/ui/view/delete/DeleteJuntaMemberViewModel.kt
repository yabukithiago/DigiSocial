package com.examples.digisocial.ui.view.delete

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.BeneficiaryState
import com.examples.digisocial.data.repository.JuntaMemberRepository

data class DeleteJuntaMemberState(
    var nome: String = "",
    var telefone: String = "",
    override var nacionalidade: String = "",
    var agregadoFamiliar: String = "",
    var numeroVisitas: Int = 0,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
) : BeneficiaryState

class DeleteJuntaMemberViewModel : ViewModel() {
    var state = mutableStateOf(DeleteJuntaMemberState())
        private set

    fun deleteJuntaMember(id: String, onSuccess: () -> Unit){
        JuntaMemberRepository.deleteJuntaMember(id = id, onSuccess = onSuccess, onFailure = { } )
    }
}