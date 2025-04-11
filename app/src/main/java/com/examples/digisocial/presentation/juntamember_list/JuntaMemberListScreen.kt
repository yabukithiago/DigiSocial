package com.examples.digisocial.presentation.juntamember_list

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
import com.examples.digisocial.presentation.juntamember_list.components.EmptyJuntaMemberListContent
import com.examples.digisocial.presentation.juntamember_list.components.JuntaMemberListContent


@Composable
fun JuntaMemberListScreen(navController: NavController, viewModel: JuntaMemberListViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var deletingVoluntary by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = "Membros da Junta",
                navController = navController,
            )
        }
    ) { innerPadding ->
        when (val juntaMemberListResponse = viewModel.juntaMemberListResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> juntaMemberListResponse.data?.let { juntaMemberList ->
                if (juntaMemberList.isEmpty()) {
                    EmptyJuntaMemberListContent()
                } else {
                    JuntaMemberListContent(
                        innerPadding = innerPadding,
                        juntaMemberList = juntaMemberList,
                        onDeleteJuntaMember = { id ->
                            viewModel.deleteJuntaMember(id)
                            deletingVoluntary = true
                        }
                    )
                }
            }

            is Response.Failure -> printError(juntaMemberListResponse.e)
        }
    }

    if (deletingVoluntary) {
        when (val deleteVoluntaryResponse = viewModel.deleteJuntaMemberResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                showToastMessage(context, R.string.juntamember_deleted)
                deletingVoluntary = false
            }

            is Response.Failure -> {
                printError(deleteVoluntaryResponse.e)
                deletingVoluntary = false
            }
        }
    }
}