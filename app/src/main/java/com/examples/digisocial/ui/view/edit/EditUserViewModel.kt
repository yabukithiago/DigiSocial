package com.examples.digisocial.ui.view.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.BeneficiaryState
import com.examples.digisocial.data.repository.UserRepository

data class EditUserState(
    var nome: String = "",
    var email: String = "",
    var telefone: String = "",
    override var nacionalidade: String = "",
    var agregadoFamiliar: String = "",
    var numeroVisitas: Int = 0,
    var status: String = "pendente",
    var role: String = "",
    var privileged: Boolean = false,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
) : BeneficiaryState

class EditUserViewModel : ViewModel() {
    var state = mutableStateOf(EditUserState())
        private set

    private val nome
        get() = state.value.nome
    private val telefone
        get() = state.value.telefone
    private val role
        get() = state.value.role
    private val privileged
        get() = state.value.privileged

    fun onNomeChange(newValue: String) {
        state.value = state.value.copy(nome = newValue)
    }

    fun onTelefoneChange(newValue: String) {
        state.value = state.value.copy(telefone = newValue)
    }

    fun update(id: String){
        UserRepository.updateUser(id = id, nome = nome,
            telefone = telefone, role = role, privileged = privileged,
            onSuccess = { }, onFailure = { } )
    }
}