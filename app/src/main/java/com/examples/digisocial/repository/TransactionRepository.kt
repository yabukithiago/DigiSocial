package com.examples.digisocial.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.examples.digisocial.data.models.Transaction
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object TransactionRepository {
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val transactionsCollection = db.collection("transactions")

    suspend fun addTransaction(transaction: Transaction) {
        val document = transactionsCollection.document()
        val transactionWithId = transaction.copy(id = document.id)
        document.set(transactionWithId).await()
    }

    fun getAll(onSuccess: (List<Transaction>) -> Unit) {
        db.collection("transactions")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }

                val listTransaction = mutableListOf<Transaction>()
                value?.let {
                    for (document in it.documents) {
                        try {
                            document.data?.let { data ->
                                val transaction = Transaction.fromMap(data)
                                listTransaction.add(transaction)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Erro ao converter documento: ${document.id}", e)
                        }
                    }
                }
                onSuccess(listTransaction)
            }
    }
}
