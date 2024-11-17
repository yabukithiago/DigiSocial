package com.examples.digisocial.repository

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
        numeroVisitas: Int,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {

        val uid = auth.currentUser?.uid

        val beneficiary = uid?.let {
            Beneficiary(
                id = "", nome = nome, telefone = telefone,
                nacionalidade = nacionalidade, agregadoFamiliar = agregadoFamiliar,
                numeroVisitas = numeroVisitas, ownerId = it
            )
        }

        if (beneficiary != null) {
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