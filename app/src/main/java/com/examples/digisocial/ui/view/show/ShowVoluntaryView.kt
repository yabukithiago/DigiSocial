package com.examples.digisocial.ui.view.show

import com.examples.digisocial.ui.components.bars.SearchBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.ui.components.cards.VoluntaryCard

@Composable
fun ShowVoluntaryView(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel: ShowVoluntaryViewModel = viewModel()
    val state by viewModel.state
    var searchQuery by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(title = "Voluntários", navController = navController)

            Spacer(modifier = Modifier.height(8.dp))

            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { searchQuery = it }
            )

            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                val filteredList = state.listVoluntary.filter {
                    it.nome.contains(searchQuery, ignoreCase = true)
                }

                if (filteredList.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum voluntário encontrado",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    itemsIndexed(
                        items = filteredList
                    ) { _, item ->
                        VoluntaryCard(
                            navController = navController,
                            id = item.id,
                            nome = item.nome,
                            email = item.email,
                            telefone = item.telefone,
                            isPrivileged = item.privileged,
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect (key1 = Unit){
        viewModel.loadListVoluntary()
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewShowVoluntaryView(){
    ShowVoluntaryView(navController = rememberNavController())
}
