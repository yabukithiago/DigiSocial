package com.examples.digisocial.data.repository

import com.examples.digisocial.data.models.Visit
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Date

object VisitRepository {
    private val db by lazy { Firebase.firestore }

    fun addVisit(
        id: String, data: Date,
        onSuccess: () -> Unit, onFailure: (String) -> Unit
    ) {

        val listVisit = Visit(id = "", data = data)

        db.collection("beneficiary")
            .document(id)
            .collection("visits")
            .add(listVisit)
            .addOnSuccessListener {
                db.collection("beneficiary")
                    .document(id)
                    .collection("visits")
                    .document(it.id)
                    .update("id", it.id)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        onFailure("Erro ao criar no firebase")
                    }
            }
    }
}