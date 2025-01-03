package com.examples.digisocial.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.examples.digisocial.data.models.User
import com.google.firebase.firestore.FirebaseFirestore

object UserRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }

    fun getAll(onSuccess: (List<User>) -> Unit) {
        db.collection("user")
            .whereEqualTo("status", "pendente")
            .addSnapshotListener { value, error ->
                if(error != null){
                    Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }

                val listUser = mutableListOf<User>()
                value?.let {
                    for (document in it.documents) {
                        document.data?.let { data ->
                            listUser.add(User.fromMap(data))
                        }
                    }
                }
                onSuccess(listUser)
            }
    }

    fun updateUser(
        id: String,
        nome: String,
        telefone: String,
        role: String,
        privileged: Boolean,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val updates = mapOf(
            "nome" to nome,
            "telefone" to telefone,
            "role" to role,
            "status" to "ativo",
            "privileged" to privileged
        )

        db.collection("user")
            .document(id)
            .update(updates)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure("Erro ao atualizar beneficiário: ${e.message}")
            }
    }

    fun deleteUser(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("user").document(id)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}