package com.examples.digisocial.data

import com.examples.digisocial.models.Voluntary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseRepository {
    fun registerVolunteer(email: String, password: String, nome: String, telefone: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
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
                                onSuccess("Usuário criado com sucesso")
                            }
                            .addOnFailureListener { e ->
                                onFailure("Erro ao registrar no Firestore: ${e.message}")
                            }
                    }
                } else {
                    onFailure("Erro ao criar usuário: ${task.exception?.message}")
                }
            }
    }

//    fun registerBeneficiary(name: String, details: Map<String, Any>, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
//        val beneficiaryData = hashMapOf(
//            "name" to name,
//            "details" to details,
//            "registeredBy" to auth.currentUser?.uid
//        )
//
//        db.collection("beneficiarios").add(beneficiaryData)
//            .addOnSuccessListener {
//                onSuccess()
//            }
//            .addOnFailureListener { e ->
//                onFailure("Erro ao registrar beneficiário: ${e.message}")
//            }
//    }
}
