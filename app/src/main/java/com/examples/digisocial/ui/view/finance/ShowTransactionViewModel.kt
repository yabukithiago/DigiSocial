package com.examples.digisocial.ui.view.finance

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.models.Transaction
import com.examples.digisocial.data.repository.TransactionRepository

data class ShowTransactionState(
    val listTransaction: List<Transaction> = emptyList(),
    var description: String = "",
    var amount: String = "",
    var type: String = "",
    var date: Long = System.currentTimeMillis(),
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class ShowTransactionViewModel : ViewModel() {
    var state = mutableStateOf(ShowTransactionState())
        private set

    var repository = TransactionRepository
        private set

    fun loadListTransaction() {
        TransactionRepository.getAll { listTransaction ->
            state.value = state.value.copy(
                listTransaction = listTransaction
            )
        }
    }
}