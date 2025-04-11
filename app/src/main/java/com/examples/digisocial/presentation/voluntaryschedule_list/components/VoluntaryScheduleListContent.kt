package com.examples.digisocial.presentation.voluntaryschedule_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.examples.digisocial.domain.models.Voluntary
import com.examples.digisocial.presentation.components.cards.VoluntaryCard

@Composable
fun VoluntaryScheduleListContent(innerPadding: PaddingValues, voluntaryList: List<Voluntary>) {
    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(voluntaryList) { item ->
                    VoluntaryCard(item, onDeleteVoluntary = { })
                }
            }
        }
    }
}