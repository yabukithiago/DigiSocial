package com.examples.digisocial.presentation.schedule_list.components

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
import com.examples.digisocial.domain.models.Schedule
import com.examples.digisocial.domain.models.Voluntary
import com.examples.digisocial.presentation.beneficiary_list.components.NON_EXISTENT_Beneficiary_ID
import com.examples.digisocial.presentation.components.cards.ScheduleCard

const val NON_EXISTENT_SCHEDULE_ID = "NO_ID"

@Composable
fun ScheduleListContent(innerPadding: PaddingValues, scheduleList: List<Schedule>, onScheduleClick: (Schedule) -> Unit, onVoluntaryRegistration: (Voluntary) -> Unit, onDeleteVoluntary: (Voluntary) -> Unit, onEditSchedule: (Schedule) -> Unit, onDeleteSchedule: (String) -> Unit) {
    var editScheduleId by remember { mutableStateOf(NON_EXISTENT_SCHEDULE_ID) }

    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val filteredSchedule = scheduleList.sortedByDescending { it.data }
                items(
                    items = filteredSchedule,
                    key = { schedule ->
                        schedule.id
                    }
                ) { schedule ->
                    if (editScheduleId != schedule.id) {
                        ScheduleCard(
                            schedule = schedule,
                            onClick = {
                                onScheduleClick(schedule)
                            },
                            onVoluntaryRegistration = { voluntary ->
                                onVoluntaryRegistration(voluntary)
                            },
                            onDeleteVoluntary = { voluntary ->
                                onDeleteVoluntary(voluntary)
                            },
                            onEditSchedule = {
                                editScheduleId = schedule.id
                            },
                            onDeleteSchedule = {
                                onDeleteSchedule(schedule.id)
                                editScheduleId = NON_EXISTENT_Beneficiary_ID
                            }
                        )
                    } else {
                        EditScheduleView(
                            id = schedule.id,
                            onDismiss = {
                                editScheduleId = NON_EXISTENT_Beneficiary_ID
                            },
                            onEditSchedule = { updatedSchedule ->
                                updatedSchedule.apply {
                                    if (vagasTotais == 0) {
                                        // Handle empty name case
                                    } else {
                                        onEditSchedule(updatedSchedule)
                                    }
                                }
                            })
                    }
                }
            }
        }
    }
}