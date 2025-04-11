package com.examples.digisocial.presentation.juntamember_list.components

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
import com.examples.digisocial.domain.models.JuntaMember
import com.examples.digisocial.presentation.components.bars.SearchBar
import com.examples.digisocial.presentation.components.cards.JuntaMemberCard

@Composable
fun JuntaMemberListContent(innerPadding: PaddingValues, juntaMemberList: List<JuntaMember>, onDeleteJuntaMember: (String)-> Unit) {
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
                val filteredJuntaMember = if (searchQuery.isNotBlank()) {
                    juntaMemberList.filter { it.nome.contains(searchQuery, ignoreCase = true) }
                } else {
                    juntaMemberList
                }
                items(
                    items = filteredJuntaMember,
                    key = { juntamember ->
                        juntamember.id
                    }
                ) { juntamember ->
                    JuntaMemberCard(
                        juntaMember = juntamember,
                        onDeleteJuntaMember = {
                            onDeleteJuntaMember(juntamember.id)
                        }
                    )
                }
            }
        }
    }
}