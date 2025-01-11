package com.examples.digisocial.ui.view.schedule

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.models.Schedule
import com.examples.digisocial.data.repository.ScheduleRepository

data class ShowScheduleState(
    val listSchedule: List<Schedule> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ShowScheduleViewModel : ViewModel() {
    var state = mutableStateOf(ShowScheduleState())
        private set

    fun loadListSchedule() {
        ScheduleRepository.getAll { listSchedule ->
            state.value = state.value.copy(
                listSchedule = listSchedule
            )
        }
    }
}