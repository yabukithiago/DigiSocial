package com.examples.digisocial.ui.view.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.models.Voluntary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class RegisterState(
    var nome: String = "",
    var telefone: String = "",
    var email: String = "",
    var password: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)
class RegisterVolunteerViewModel : ViewModel() {
    var state = mutableStateOf(RegisterState())
        private set

    private val email
        get() = state.value.email
    private val password
        get() = state.value.password

    fun onEmailChange(newValue: String) {
        state.value = state.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        state.value = state.value.copy(password = newValue)
    }

    fun onNomeChange(newValue: String) {
        state.value = state.value.copy(nome = newValue)
    }

    fun onTelefoneChange(newValue: String) {
        state.value = state.value.copy(telefone = newValue)
    }

    fun registerVolunteer(email: String, password: String, nome: String, telefone: String,
                          onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
//        var state = mutableStateOf(RepositoryState())
        val auth = FirebaseAuth.getInstance()
//        var currentUser = auth.currentUser
        val db = FirebaseFirestore.getInstance()

        /*if (currentUser == null) {
            state.value = state.value.copy(errorMessage = "Usuário não logado")
            return
        }*/

        // Cria um novo voluntário no Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid
                    if (userId != null) {
                        val voluntary = Voluntary(id = userId, email = email, telefone = telefone, nome = nome)
                        db.collection("user").document(userId)
                            .set(voluntary)
                            .addOnSuccessListener {
                                onSuccess("Voluntário criado com sucesso")
                            }
                            .addOnFailureListener { e ->
                                onFailure("Erro ao registrar no Firestore: ${e.message}")
                            }
                    }
                } else {
                    onFailure("Erro ao criar voluntário: ${task.exception?.message}")
                }
            }
    }
}
