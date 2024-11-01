package com.examples.digisocial.data

import com.examples.digisocial.models.Voluntary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun registerVolunteer(email: String, password: String, nome: String, telefone: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        // Cria um novo usuário no Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid
                    if (userId != null) {
                        val voluntary = Voluntary(id = userId, email = email, telefone = telefone, nome = nome)
                        db.collection("user").document(userId)
                            .set(voluntary)
                            .addOnSuccessListener {
                                onSuccess()
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

    fun registerBeneficiary(name: String, details: Map<String, Any>, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val beneficiaryData = hashMapOf(
            "name" to name,
            "details" to details,
            "registeredBy" to auth.currentUser?.uid
        )

        db.collection("beneficiarios").add(beneficiaryData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure("Erro ao registrar beneficiário: ${e.message}")
            }
    }
}
