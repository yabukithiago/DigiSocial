package com.examples.digisocial.presentation.voluntary_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.examples.digisocial.domain.models.Voluntary
import com.examples.digisocial.presentation.components.bars.SearchBar
import com.examples.digisocial.presentation.components.cards.VoluntaryCard

@Composable
fun VoluntaryListContent(innerPadding: PaddingValues, voluntaryList: List<Voluntary>,
                         onDeleteVoluntary: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { searchQuery = it }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val filteredVoluntary = if (searchQuery.isNotBlank()) {
                    voluntaryList.filter { it.nome.contains(searchQuery, ignoreCase = true) }
                } else {
                    voluntaryList
                }
                items(
                    items = filteredVoluntary,
                    key = { voluntary ->
                        voluntary.id
                    }
                ) { voluntary ->
                    VoluntaryCard(
                        voluntary = voluntary,
                        onDeleteVoluntary = {
                            onDeleteVoluntary(voluntary.id)
                        }
                    )
                }
            }
        }
    }
}