package com.examples.digisocial.ui.view.show

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.components.bars.SearchBar
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.ui.components.cards.BeneficiaryCard

@Composable
fun ShowBeneficiaryView(navController: NavController, modifier: Modifier = Modifier) {

    val viewModel: ShowBeneficiaryViewModel = viewModel()
    val state by viewModel.state
    var searchQuery by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(title = "Listar Beneficiários", navController = navController)

            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { searchQuery = it }
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                val filteredList = state.listBeneficiary.filter {
                    it.nome.contains(searchQuery, ignoreCase = true)
                }

                if (filteredList.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum beneficiário encontrado",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    itemsIndexed(
                        items = filteredList
                    ) { _, item ->
                        BeneficiaryCard(
                            navController = navController,
                            id = item.id,
                            nome = item.nome,
                            telefone = item.telefone,
                            nacionalidade = item.nacionalidade,
                            agregadoFamiliar = item.agregadoFamiliar,
                            numeroVisitas = item.numeroVisitas,
                            ownerId = item.ownerId
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { navController.navigate("registerBeneficiary") },
            containerColor = Color.Blue,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adicionar beneficiário",
                tint = Color.White
            )
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.loadListBeneficiary()
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewShowBeneficiaryView(){
    ShowBeneficiaryView(navController = rememberNavController())
}
