package com.examples.digisocial.ui.view.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.models.Beneficiary
import com.google.firebase.firestore.FirebaseFirestore

data class RegisterBeneficiaryState(
    var nome: String = "",
    var telefone: String = "",
    var nacionalidade: String = "",
    var agregadoFamiliar: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class RegisterBeneficiaryViewModel : ViewModel() {
    var state = mutableStateOf(RegisterBeneficiaryState())
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

    fun registerBeneficiary(
        nome: String, telefone: String, nacionalidade: String, agregadoFamiliar: String,
        onSuccess: (String) -> Unit, onFailure: (String) -> Unit
        ) {
        val db = FirebaseFirestore.getInstance()

        val beneficiary = Beneficiary(id = null.toString(), nome = nome, telefone = telefone,
            nacionalidade = nacionalidade, agregadoFamiliar = agregadoFamiliar,
            numeroVisitas = 0, pedidos = listOf()
        )

        db.collection("user")
            .add(beneficiary)
            .addOnSuccessListener { documentReference ->
                onSuccess("Beneficiário criado com sucesso com ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                onFailure("Erro ao registrar no Firestore: ${e.message}")
            }
    }
}