package com.examples.digisocial.presentation.transaction_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examples.digisocial.domain.models.Response.Loading
import com.examples.digisocial.domain.models.Transaction
import com.examples.digisocial.domain.repository.AddTransactionResponse
import com.examples.digisocial.domain.repository.TransactionListResponse
import com.examples.digisocial.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(private val repo: TransactionRepository): ViewModel(){
    var transactionListResponse by mutableStateOf<TransactionListResponse>(Loading)
        private set
    var addTransactionResponse by mutableStateOf<AddTransactionResponse>(Loading)
        private set

    init {
        getTransactions()
    }

    private fun getTransactions() = viewModelScope.launch {
        repo.getTransactions().collect { response ->
            transactionListResponse = response
        }
    }

    fun addTransaction(transaction: Transaction) = viewModelScope.launch {
        addTransactionResponse = repo.addTransaction(transaction)
        getTransactions()
    }
}