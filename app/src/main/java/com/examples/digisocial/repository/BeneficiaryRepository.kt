package com.examples.digisocial.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.examples.digisocial.data.models.Beneficiary
import com.google.firebase.firestore.FirebaseFirestore

object BeneficiaryRepository {
    val db by lazy { FirebaseFirestore.getInstance() }

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

    fun updateBeneficiary(documentId: String, updates: Map<String, Any>, onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (documentId.isEmpty()) {
            onFailure("ID do documento é inválido.")
            return
        }

        println(documentId)

        db.collection("beneficiary").document(documentId)
            .update(updates)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure("Erro ao atualizar beneficiário: ${e.message}")
            }
    }
}