package com.examples.digisocial.ui.view.schedulevoluntary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.examples.digisocial.data.models.Voluntary
import com.examples.digisocial.data.repository.ScheduleRepository.fetchVoluntary
import com.examples.digisocial.ui.components.bars.TopBar
import com.examples.digisocial.ui.components.cards.VoluntaryCard

@Composable
fun ShowVoluntaryScheduleView(navController: NavController, id: String) {
    var itemList by remember { mutableStateOf(emptyList<Voluntary>()) }

    LaunchedEffect(id) {
        fetchVoluntary(
            scheduleId = id,
            onSuccess = { fetchedVoluntary ->
                itemList = fetchedVoluntary
            },
            onFailure = {

            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar("Voluntários na Escala", navController = navController)
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(itemList) { item ->
                VoluntaryCard(navController, item.id, item.nome, item.telefone, item.email, item.privileged)
            }
        }
    }
}