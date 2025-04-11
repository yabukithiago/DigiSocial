package com.examples.digisocial.presentation.voluntary_list

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
import com.examples.digisocial.presentation.voluntary_list.components.EmptyVoluntaryListContent
import com.examples.digisocial.presentation.voluntary_list.components.VoluntaryListContent
import com.examples.digisocial.presentation.components.LoadingIndicator
import com.examples.digisocial.presentation.components.bars.TopBar

@Composable
fun VoluntaryListScreen(navController: NavController, viewModel: VoluntaryListViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var deletingVoluntary by remember { mutableStateOf(false) }
   
    Scaffold(
        topBar = {
            TopBar(
                title = "Voluntários",
                navController = navController,
            )
        }
    ) { innerPadding ->
        when (val voluntaryListResponse = viewModel.voluntaryListResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> voluntaryListResponse.data?.let { voluntaryList ->
                if (voluntaryList.isEmpty()) {
                    EmptyVoluntaryListContent()
                } else {
                    VoluntaryListContent(
                        innerPadding = innerPadding,
                        voluntaryList = voluntaryList,
                        onDeleteVoluntary = { id ->
                            viewModel.deleteVoluntary(id)
                            deletingVoluntary = true
                        }
                    )
                }
            }

            is Response.Failure -> printError(voluntaryListResponse.e)
        }
    }
    if (deletingVoluntary) {
        when (val deleteVoluntaryResponse = viewModel.deleteVoluntaryResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                showToastMessage(context, R.string.voluntary_deleted)
                deletingVoluntary = false
            }

            is Response.Failure -> {
                printError(deleteVoluntaryResponse.e)
                deletingVoluntary = false
            }
        }
    }
}