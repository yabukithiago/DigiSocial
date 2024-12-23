package com.examples.digisocial.ui.view.finance

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.models.Transaction
import com.examples.digisocial.data.repository.TransactionRepository

data class FinanceState(
    val listTransaction: List<Transaction> = emptyList(),
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0
)

class FinanceDashboardViewModel : ViewModel() {
    var state = mutableStateOf(FinanceState())
        private set

    fun loadListTransaction() {
        TransactionRepository.getAll { transactions ->
            calculateTotals(transactions)
        }
    }

    private fun calculateTotals(transactions: List<Transaction>) {
        val income = transactions.filter { it.type == "ENTRADA" }.sumOf { it.amount }
        val expense = transactions.filter { it.type == "SAIDA" }.sumOf { it.amount }

        state.value = state.value.copy(
            listTransaction = transactions,
            totalIncome = income,
            totalExpense = expense
        )
    }
}