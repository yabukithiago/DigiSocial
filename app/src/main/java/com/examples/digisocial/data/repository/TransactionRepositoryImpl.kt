package com.examples.digisocial.data.repository

import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.Transaction
import com.examples.digisocial.domain.repository.TransactionRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TransactionRepositoryImpl(private val transactionRef: CollectionReference) : TransactionRepository {
    override fun getTransactions() = callbackFlow {
        val listener = transactionRef.addSnapshotListener{ transaction, e ->
            val transactionListResponse = if (transaction != null) {
                val transactionList = transaction.map { transactionSnapshot ->
                    transactionSnapshot.toTransaction()
                }
                Response.Success(transactionList)
            } else {
                Response.Failure(e)
            }
            trySend(transactionListResponse)
        }
        awaitClose{
            listener.remove()
        }
    }

    override suspend fun addTransaction(transaction: Transaction) : Response<String> {
        return try {
            val id = transactionRef.document().id
            val transactionId = transaction.copy(id = id)

            transactionRef.document(id).set(transactionId).await()
            Response.Success(id)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}

fun DocumentSnapshot.toTransaction() = Transaction(
    id = id,
    description = getString("description") ?: "",
    amount = getDouble("amount") ?: 0.0,
    type = getString("type") ?.let { type ->
        when(type) {
            "ENTRADA" -> Transaction.Type.ENTRADA
            "SAIDA" -> Transaction.Type.SAIDA
            else -> Transaction.Type.ENTRADA
        }
    } ?: Transaction.Type.ENTRADA,
    date = getLong("date") ?: 0L,
).apply {
    id = getId()
}