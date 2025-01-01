package com.examples.digisocial.ui.view.show

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.ui.components.cards.JuntaMemberCard

@Composable
fun ShowJuntaMemberView(navController: NavController, modifier: Modifier = Modifier) {

    val viewModel: ShowJuntaMemberViewModel = viewModel()
    val state by viewModel.state

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            TopBar(title = "Membros da Junta", navController = navController)
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = state.listJuntaMember
                ) { _, item ->
                    JuntaMemberCard(
                        navController = navController,
                        nome = item.nome,
                        email = item.email,
                        telefone = item.telefone,
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = { navController.navigate("registerJuntaMember") },
            containerColor = Color.Blue,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adicionar membro da junta",
                tint = Color.White
            )
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.loadListJuntaMember()
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewShowJuntaMemberView(){
    ShowJuntaMemberView(navController = rememberNavController())
}
