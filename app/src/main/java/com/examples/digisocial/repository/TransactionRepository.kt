package com.examples.digisocial.repository

import com.examples.digisocial.data.models.Transaction
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TransactionRepository {
    private val db = FirebaseFirestore.getInstance()
    private val transactionsCollection = db.collection("transactions")

    suspend fun addTransaction(transaction: Transaction) {
        val document = transactionsCollection.document()
        val transactionWithId = transaction.copy(id = document.id)
        document.set(transactionWithId).await()
    }
}
