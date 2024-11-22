package com.examples.digisocial.ui.view.finance

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examples.digisocial.data.models.Transaction
import com.examples.digisocial.repository.TransactionRepository
import kotlinx.coroutines.launch

data class FinanceState(
    val listTransaction: List<Transaction> = emptyList(),
    var description: String = "",
    var amount: String = "",
    var type: String = "",
    var date: Long = System.currentTimeMillis(),
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class FinanceViewModel : ViewModel() {
    var state = mutableStateOf(FinanceState())
        private set

    var repository = TransactionRepository
        private set

    fun onDescriptionChange(newValue: String) {
        state.value = state.value.copy(description = newValue)
    }

    fun onAmountChange(newValue: String) {
        state.value = state.value.copy(amount = newValue)
    }

    fun onTypeChange(newValue: String) {
        state.value = state.value.copy(type = newValue)
    }

    fun addTransaction(description: String, amount: Double, type: String, onSuccess: () -> Unit
    , onFailure: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.addTransaction(
                    Transaction(
                        description = description,
                        amount = amount,
                        type = type
                    )
                )
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun loadListTransaction() {
        TransactionRepository.getAll { listTransaction ->
            state.value = state.value.copy(
                listTransaction = listTransaction
            )
        }
    }
}
