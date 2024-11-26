package com.examples.digisocial.ui.view.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.BeneficiaryState
import com.examples.digisocial.data.repository.UserRepository

data class DeleteUserState(
    var nome: String = "",
    var telefone: String = "",
    override var nacionalidade: String = "",
    var agregadoFamiliar: String = "",
    var numeroVisitas: Int = 0,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
) : BeneficiaryState

class DeleteUserViewModel : ViewModel() {
    var state = mutableStateOf(DeleteUserState())
        private set

    fun deleteUser(id: String, onSuccess: () -> Unit){
        UserRepository.deleteUser(id = id, onSuccess = onSuccess, onFailure = { } )
    }
}