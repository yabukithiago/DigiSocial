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
import com.examples.digisocial.ui.components.cards.VoluntaryCard

@Composable
fun ShowVoluntaryView(navController: NavController, modifier: Modifier = Modifier) {

    val viewModel: ShowVoluntaryViewModel = viewModel()
    val state by viewModel.state

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            TopBar(title = "Listar Voluntários", navController = navController)
            LazyColumn(contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(
                    items = state.listVoluntary
                ) { _, item ->
                    VoluntaryCard (
                        navController = navController,
                        nome = item.nome,
                        email = item.email,
                        telefone = item.telefone,
                    )
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
