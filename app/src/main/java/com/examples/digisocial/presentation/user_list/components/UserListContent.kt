package com.examples.digisocial.presentation.user_list.components

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
import com.examples.digisocial.domain.models.User
import com.examples.digisocial.presentation.components.bars.SearchBar
import com.examples.digisocial.presentation.components.cards.UserCard

@Composable
fun UserListContent(
    innerPadding: PaddingValues, userList: List<User>,
    onUpdateUser: (User) -> Unit,
    onDeleteUser: (String) -> Unit
) {
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
                val filteredUser = if (searchQuery.isNotBlank()) {
                    userList.filter { it.nome.contains(searchQuery, ignoreCase = true) }
                } else {
                    userList
                }
                items(
                    items = filteredUser,
                    key = { user ->
                        user.id
                    }
                ) { user ->
                    UserCard(
                        user = user,
                        onUpdateUser = {
                            onUpdateUser(user)
                        },
                        onDeleteUser = {
                            onDeleteUser(user.id)
                        }
                    )
                }
            }
        }
    }
}