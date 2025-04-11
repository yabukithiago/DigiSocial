package com.examples.digisocial.domain.repository

import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.Transaction
import kotlinx.coroutines.flow.Flow

typealias TransactionListResponse = Response<List<Transaction>>
typealias AddTransactionResponse = Response<String>

interface TransactionRepository {
    fun getTransactions(): Flow<TransactionListResponse>

    suspend fun addTransaction(transaction: Transaction): AddTransactionResponse
}
