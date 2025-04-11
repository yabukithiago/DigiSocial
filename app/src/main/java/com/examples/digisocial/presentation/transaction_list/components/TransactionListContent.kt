package com.examples.digisocial.presentation.transaction_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.examples.digisocial.domain.models.Transaction
import com.examples.digisocial.presentation.components.cards.TransactionCard

@Composable
fun TransactionListContent(innerPadding: PaddingValues, transactionList: List<Transaction>) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val type = Transaction.Type.entries.toTypedArray()

    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(
                modifier = Modifier.fillMaxWidth(),
                selectedTabIndex = selectedTabIndex
            ) {
                type.forEachIndexed { index, type ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = type.value) }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val filteredTransactions =
                    transactionList.filter { it.type == type[selectedTabIndex] }
                items(
                    items = filteredTransactions,
                    key = { transaction ->
                        transaction.id
                    }
                ) { transaction ->
                    TransactionCard(
                        transaction = transaction
                    )
                }
            }
        }
    }
}