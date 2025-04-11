package com.examples.digisocial.presentation.transaction_list

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.examples.digisocial.R
import com.examples.digisocial.core.printError
import com.examples.digisocial.core.showToastMessage
import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.presentation.components.LoadingIndicator
import com.examples.digisocial.presentation.components.bars.TopBar
import com.examples.digisocial.presentation.components.buttons.AddFloatingActionButton
import com.examples.digisocial.presentation.transaction_list.components.EmptyTransactionListContent
import com.examples.digisocial.presentation.transaction_list.components.TransactionListContent
import com.examples.digisocial.ui.view.finance.CreateTransactionView

@Composable
fun TransactionListScreen(navController: NavController, viewModel: TransactionListViewModel = hiltViewModel()) { 
    val context = LocalContext.current
    var openAddTransactionDialog by remember { mutableStateOf(false) }
    var addingTransaction by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = "Transações",
                navController = navController,
            )
        },
        floatingActionButton = {
            AddFloatingActionButton {
                openAddTransactionDialog = true
            }
        }
    ) { innerPadding ->
        when (val transactionListResponse = viewModel.transactionListResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> transactionListResponse.data?.let { transactionList ->
                if (transactionList.isEmpty()) {
                    EmptyTransactionListContent()
                } else {
                    TransactionListContent(
                        innerPadding = innerPadding,
                        transactionList = transactionList
                    )
                }
            }

            is Response.Failure -> printError(transactionListResponse.e)
        }
    }

    if (openAddTransactionDialog) {
        CreateTransactionView(
            onDismiss = {
                openAddTransactionDialog = false
            },
            onCreateTransaction = { transaction ->
                viewModel.addTransaction(transaction)
                addingTransaction = true
                showToastMessage(context, R.string.transaction_added)
            },
        )
    }

    if (addingTransaction) {
        when (val addTransactionResponse = viewModel.addTransactionResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                showToastMessage(context, R.string.voluntary_deleted)
                addingTransaction = false
            }

            is Response.Failure -> {
                printError(addTransactionResponse.e)
                addingTransaction = false
            }
        }
    }
}