package com.examples.digisocial.ui.view.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

data class RegisterState(
    val name: String = "",
    val telefone: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val status: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class RegisterViewModel : ViewModel() {
    var state = mutableStateOf(RegisterState())
        private set
    private val auth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore

    fun onNameChange(newValue: String) {
        state.value = state.value.copy(name = newValue)
    }

    fun onEmailChange(newValue: String) {
        state.value = state.value.copy(email = newValue)
    }

    fun onTelefoneChange(newValue: String){
        state.value = state.value.copy(telefone = newValue)
    }

    fun onPasswordChange(newValue: String) {
        state.value = state.value.copy(password = newValue)
    }

    fun onConfirmPasswordChange(newValue: String) {
        state.value = state.value.copy(confirmPassword = newValue)
    }

    fun register(onRegisterSuccess: () -> Unit, onRegisterFailure: (String) -> Unit) {
        state.value = state.value.copy(isLoading = true)

        if (state.value.password != state.value.confirmPassword) {
            onRegisterFailure("As senhas não coincidem")
            state.value = state.value.copy(isLoading = false)
            return
        }

        auth.createUserWithEmailAndPassword(state.value.email, state.value.password)
            .addOnCompleteListener { task ->
                state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val user = mapOf(
                        "id" to userId,
                        "nome" to state.value.name,
                        "telefone" to state.value.telefone,
                        "email" to state.value.email,
                        "role" to "",
                        "privileged" to false,
                        "status" to "pendente"
                    )

                    firestore.collection("user").document(userId ?: "")
                        .set(user)
                        .addOnSuccessListener {
                            state.value = state.value.copy(isLoading = false)
                            onRegisterSuccess()
                        }
                        .addOnFailureListener { e ->
                            state.value = state.value.copy(isLoading = false)
                            onRegisterFailure("Erro ao salvar na base de dados.")
                        }
                } else {
                    state.value = state.value.copy(isLoading = false)
                    val errorMessage = task.exception?.localizedMessage ?: "Erro desconhecido"
                    onRegisterFailure(errorMessage)
                }
            }
    }
}
