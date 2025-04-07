package com.examples.digisocial.ui.view.delete

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.domain.repository.UserRepository

data class DeleteUserState(
    var nome: String = "",
    var telefone: String = "",
    var nacionalidade: String = "",
    var agregadoFamiliar: String = "",
    var numeroVisitas: Int = 0,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class DeleteUserViewModel : ViewModel() {
    var state = mutableStateOf(DeleteUserState())
        private set

    fun deleteUser(id: String, onSuccess: () -> Unit){
        UserRepository.deleteUser(id = id, onSuccess = onSuccess, onFailure = { } )
    }
}