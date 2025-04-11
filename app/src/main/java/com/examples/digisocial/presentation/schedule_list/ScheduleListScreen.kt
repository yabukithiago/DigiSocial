package com.examples.digisocial.presentation.schedule_list

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
import com.examples.digisocial.domain.models.Voluntary
import com.examples.digisocial.presentation.Screen
import com.examples.digisocial.presentation.components.LoadingIndicator
import com.examples.digisocial.presentation.components.bars.TopBar
import com.examples.digisocial.presentation.components.buttons.AddFloatingActionButton
import com.examples.digisocial.presentation.schedule_list.components.EmptyScheduleListContent
import com.examples.digisocial.presentation.schedule_list.components.ScheduleListContent
import com.examples.digisocial.ui.view.schedule.CreateScheduleView

@Composable
fun ScheduleListScreen(navController: NavController, viewModel: ScheduleListViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var openAddSchedule by remember { mutableStateOf(false) }
    var creatingSchedule by remember { mutableStateOf(false) }
    var updatingSchedule by remember { mutableStateOf(false) }
    var deletingSchedule by remember { mutableStateOf(false) }
    var scheduleVoluntary by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = "Escalas",
                navController = navController,
            )
        },
        floatingActionButton = {
            AddFloatingActionButton {
                openAddSchedule = true
            }
        }
    ) { innerPadding ->
        when (val scheduleListResponse = viewModel.scheduleListResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> scheduleListResponse.data?.let { scheduleList ->
                if (scheduleList.isEmpty()) {
                    EmptyScheduleListContent()
                } else {
                    ScheduleListContent(
                        innerPadding = innerPadding,
                        scheduleList = scheduleList,
                        onScheduleClick = { schedule ->
                            navController.navigate(
                                Screen.ScheduleDetailsScreen.createRoute(
                                    schedule.id
                                )
                            )
                        },
                        onVoluntaryRegistration = { schedule ->
                            viewModel.addVoluntaryToSchedule(schedule.id, Voluntary(
                                id = "",
                                nome = "" ,
                                telefone = "",
                                email = "",
                            ))
                            scheduleVoluntary = true
                        },
                        onDeleteVoluntary = { voluntary ->
//                            viewModel.deleteVoluntaryFromSchedule(it)
                            scheduleVoluntary = true
                        },
                        onEditSchedule = { schedule ->
                            viewModel.updateSchedule(schedule)
                            updatingSchedule = true
                        },
                        onDeleteSchedule = { id ->
                            viewModel.deleteSchedule(id)
                            deletingSchedule = true
                        }
                    )
                }
            }

            is Response.Failure -> printError(scheduleListResponse.e)
        }
    }

    if (openAddSchedule) {
        CreateScheduleView(
            onDismiss = {
                openAddSchedule = false
            },
            onCreateSchedule = { schedule ->
                viewModel.addSchedule(schedule)
                creatingSchedule = true
                showToastMessage(context, R.string.schedule_added)
            },
        )
    }

    if (updatingSchedule) {
        when (val updateScheduleResponse = viewModel.updateScheduleResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                showToastMessage(context, R.string.schedule_updated)
                updatingSchedule = false
            }

            is Response.Failure -> {
                printError(updateScheduleResponse.e)
                updatingSchedule = false
            }
        }
    }

    if (deletingSchedule) {
        when (val deleteScheduleResponse = viewModel.deleteScheduleResponse) {
            is Response.Loading -> LoadingIndicator()
            is Response.Success -> {
                showToastMessage(context, R.string.schedule_deleted)
                deletingSchedule = false
            }

            is Response.Failure -> {
                printError(deleteScheduleResponse.e)
                deletingSchedule = false
            }
        }
    }
}