package com.examples.digisocial.ui.view.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.models.Beneficiary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class RegisterBeneficiaryState(
    var nome: String = "",
    var telefone: String = "",
    var nacionalidade: String = "",
    var agregadoFamiliar: String = "",
    var numeroVisitas: Int = 0,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class RegisterBeneficiaryViewModel : ViewModel() {
    var state = mutableStateOf(RegisterBeneficiaryState())
        private set

    private val db = FirebaseFirestore.getInstance()

    private val auth = FirebaseAuth.getInstance()

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
        nome: String,
        telefone: String,
        nacionalidade: String,
        agregadoFamiliar: String,
        numeroVisitas: Int,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val uid = auth.currentUser?.uid

        if (uid == null) {
            state.value = state.value.copy(errorMessage = "Usuário não logado")
            return
        }

        val beneficiary = Beneficiary(
            id = "", nome = nome, telefone = telefone,
            nacionalidade = nacionalidade, agregadoFamiliar = agregadoFamiliar,
            numeroVisitas = numeroVisitas, ownerId = uid
        )

        db.collection("beneficiary")
            .add(beneficiary)
            .addOnCompleteListener { documentReference ->
                val generatedId = documentReference.result.id

                db.collection("beneficiary")
                    .document(generatedId)
                    .update("id", generatedId)
                    .addOnSuccessListener {
                        onSuccess("Beneficiário editado com sucesso: $generatedId")
                    }
                    .addOnFailureListener { e ->
                        onFailure(e.message ?: "Erro ao adicionar beneficiário")
                    }
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Erro ao adicionar beneficiário")
            }
    }

    fun updateBeneficiary(
        id: String,
        nome: String,
        telefone: String,
        nacionalidade: String,
        agregadoFamiliar: String,
        numeroVisitas: Int,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val updates = mapOf(
            "nome" to nome,
            "telefone" to telefone,
            "nacionalidade" to nacionalidade,
            "agregadoFamiliar" to agregadoFamiliar,
            "numeroVisitas" to numeroVisitas
        )

        db.collection("beneficiary")
            .document(id)
            .update(updates)
            .addOnSuccessListener {
                onSuccess("Beneficiário editado com sucesso: $id")
            }
            .addOnFailureListener { e ->
                onFailure("Erro ao atualizar beneficiário: ${e.message}")
            }
    }
}