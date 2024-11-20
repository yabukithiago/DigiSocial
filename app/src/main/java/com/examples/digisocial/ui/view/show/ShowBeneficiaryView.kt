package com.examples.digisocial.ui.view.show

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.ui.components.cards.BeneficiaryCard

@Composable
fun     ShowBeneficiaryView(navController: NavController, modifier: Modifier = Modifier) {

    val viewModel: ShowBeneficiaryViewModel = viewModel()
    val state by viewModel.state

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            TopBar(title = "Listar Beneficiários", navController = navController)
            LazyColumn(contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(
                    items = state.listBeneficiary
                ) { _, item ->
                    BeneficiaryCard (
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
    LaunchedEffect (key1 = Unit){
        viewModel.loadListBeneficiary()
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewShowBeneficiaryView(){
    ShowBeneficiaryView(navController = rememberNavController())
}
