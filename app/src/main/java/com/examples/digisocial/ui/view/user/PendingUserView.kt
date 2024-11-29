package com.examples.digisocial.ui.view.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.examples.digisocial.ui.components.bars.SearchBar
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.ui.components.cards.PendingUserCard

@Composable
fun PendingUserView(navController: NavController) {
    val viewModel: PendingUserViewModel = viewModel()
    val state by viewModel.state
    var searchQuery by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(title = "Utilizadores Pendentes", navController = navController)

            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { searchQuery = it }
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                val filteredList = state.listUser.filter {
                    it.nome.contains(searchQuery, ignoreCase = true)
                }

                if (filteredList.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum utilizador pendente",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    itemsIndexed(
                        items = filteredList
                    ) { _, item ->
                        PendingUserCard(
                            navController = navController,
                            id = item.id,
                            nome = item.nome,
                            email = item.email,
                            telefone = item.telefone
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect (key1 = Unit){
        viewModel.loadListUser()
    }
}
