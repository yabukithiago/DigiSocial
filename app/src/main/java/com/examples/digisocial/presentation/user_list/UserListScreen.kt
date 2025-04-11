package com.examples.digisocial.presentation.user_list

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.examples.digisocial.R
import com.examples.digisocial.core.printError
import com.examples.digisocial.core.showToastMessage
import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.presentation.components.LoadingIndicator
import com.examples.digisocial.presentation.components.bars.TopBar
import com.examples.digisocial.presentation.user_list.components.EmptyUserListContent
import com.examples.digisocial.presentation.user_list.components.UserListContent


@Composable
fun UserListScreen(navController: NavController, viewModel: UserListViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var updatingUser by remember { mutableStateOf(false) }
    var deletingVoluntary by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = "Utilizadores Pendentes",
                navController = navController,
            )
        }
    ) { innerPadding ->
        when (val userListResponse = viewModel.userListResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> userListResponse.data?.let { userList ->
                if (userList.isEmpty()) {
                    EmptyUserListContent()
                } else {
                    UserListContent(
                        innerPadding = innerPadding,
                        userList = userList,
                        onUpdateUser = {

                        },
                        onDeleteUser = { id ->
                            viewModel.deleteUser(id)
                            deletingVoluntary = true
                        }
                    )
                }
            }

            is Response.Failure -> printError(userListResponse.e)
        }
    }

    if (updatingUser){
        when(val updateUserResponse = viewModel.updateUserResponse){
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                showToastMessage(context, R.string.user_updated)
                updatingUser = false
            }
            is Response.Failure -> {
                printError(updateUserResponse.e)
                updatingUser = false
            }
        }
    }

    if (deletingVoluntary) {
        when (val deleteVoluntaryResponse = viewModel.deleteUserResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                showToastMessage(context, R.string.user_deleted)
                deletingVoluntary = false
            }

            is Response.Failure -> {
                printError(deleteVoluntaryResponse.e)
                deletingVoluntary = false
            }
        }
    }
}