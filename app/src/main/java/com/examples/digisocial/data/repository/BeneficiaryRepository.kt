package com.examples.digisocial.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.examples.digisocial.data.models.Beneficiary
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object BeneficiaryRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val auth = FirebaseAuth.getInstance()

    fun createBeneficiary(
        nome: String,
        telefone: String,
        nacionalidade: String,
        agregadoFamiliar: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("user").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.contains("nome")) {
                        val userName = document.getString("nome") ?: ""
                        db.collection("beneficiary")
                            .whereEqualTo("nome", nome)
                            .get()
                            .addOnSuccessListener { documents ->
                                if (documents.isEmpty) {
                                    val beneficiary = Beneficiary(
                                        id = "",
                                        nome = nome,
                                        telefone = telefone,
                                        nacionalidade = nacionalidade,
                                        agregadoFamiliar = agregadoFamiliar,
                                        numeroVisita = 0,
                                        ownerId = userName
                                    )

                                    db.collection("beneficiary")
                                        .add(beneficiary)
                                        .addOnCompleteListener { documentReference ->
                                            val generatedId = documentReference.result.id

                                            db.collection("beneficiary")
                                                .document(generatedId)
                                                .update("id", generatedId)
                                                .addOnSuccessListener {
                                                    onSuccess()
                                                }
                                        }
                                        .addOnFailureListener { e ->
                                            onFailure("Erro ao adicionar beneficiário: ${e.message}")
                                        }
                                } else {
                                    onFailure("Beneficiário com o nome '$nome' já existe.")
                                }
                            }
                            .addOnFailureListener { e ->
                                onFailure("Erro ao verificar beneficiário existente: ${e.message}")
                            }
                    } else {
                        onFailure("Erro: Usuário não encontrado ou sem nome cadastrado.")
                    }
                }
                .addOnFailureListener { e ->
                    onFailure("Erro ao obter usuário: ${e.message}")
                }
        } else {
            onFailure("Erro: Usuário não autenticado.")
        }
    }

    fun getAll(onSuccess: (List<Beneficiary>) -> Unit) {
        db.collection("beneficiary")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }

                val listBeneficiary = mutableListOf<Beneficiary>()
                value?.let {
                    for (document in it.documents) {
                        try {
                            document.data?.let { data ->
                                val beneficiary = Beneficiary.fromMap(data)
                                listBeneficiary.add(beneficiary)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Erro ao converter documento: ${document.id}", e)
                        }
                    }
                }
                onSuccess(listBeneficiary)
            }
    }

    fun updateBeneficiary(
        id: String,
        nome: String,
        telefone: String,
        nacionalidade: String,
        agregadoFamiliar: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val updates = mapOf(
            "nome" to nome,
            "telefone" to telefone,
            "nacionalidade" to nacionalidade,
            "agregadoFamiliar" to agregadoFamiliar,
        )

        db.collection("beneficiary")
            .document(id)
            .update(updates)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure("Erro ao atualizar beneficiário: ${e.message}")
            }
    }

    fun deleteBeneficiary(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("beneficiary").document(id)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}