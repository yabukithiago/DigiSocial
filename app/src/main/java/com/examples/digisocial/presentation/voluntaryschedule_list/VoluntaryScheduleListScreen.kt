package com.examples.digisocial.presentation.voluntaryschedule_list

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.examples.digisocial.core.printError
import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.presentation.components.LoadingIndicator
import com.examples.digisocial.presentation.components.bars.TopBar
import com.examples.digisocial.presentation.schedule_list.ScheduleListViewModel
import com.examples.digisocial.presentation.voluntaryschedule_list.components.EmptyVoluntaryScheduleListContent
import com.examples.digisocial.presentation.voluntaryschedule_list.components.VoluntaryScheduleListContent

@Composable
fun VoluntaryScheduleListScreen(
    navController: NavController,
    voluntaryId: String,
    viewModel: ScheduleListViewModel = hiltViewModel()
) {
    var addingVisit by remember { mutableStateOf(false) }

    LaunchedEffect(voluntaryId) {
        viewModel.getVoluntaryById(voluntaryId)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Voluntários na Escala",
                navController = navController,
            )
        }
    ) { innerPadding ->
        when (val response = viewModel.scheduleVoluntaryResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                val voluntaryList = response.data?.voluntaries ?: emptyList()
                if (voluntaryList.isEmpty()) {
                    EmptyVoluntaryScheduleListContent()
                } else {
                    VoluntaryScheduleListContent(
                        innerPadding = innerPadding,
                        voluntaryList = voluntaryList
                    )
                }
            }
            is Response.Failure -> printError(response.e)
        }
    }
    if (addingVisit) {
        when (val addResponse = viewModel.addScheduleResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> addingVisit = false
            is Response.Failure -> {
                printError(addResponse.e)
                addingVisit = false
            }
        }
    }
}