package com.examples.digisocial.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.examples.digisocial.data.models.JuntaMember
import com.google.firebase.firestore.FirebaseFirestore

object JuntaMemberRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }

    fun getAll(onSuccess: (List<JuntaMember>) -> Unit) {
        db.collection("user")
            .whereEqualTo("role", "juntamember")
            .whereEqualTo("status", "ativo")
            .addSnapshotListener { value, error ->
                if(error != null){
                    Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }

                val listJuntaMember = mutableListOf<JuntaMember>()
                value?.let {
                    for (document in it.documents) {
                        document.data?.let { data ->
                            listJuntaMember.add(JuntaMember.fromMap(data))
                        }
                    }
                }
                onSuccess(listJuntaMember)
            }
    }

    fun deleteJuntaMember(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("user").document(id)
            .update("status", "inativo")
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}