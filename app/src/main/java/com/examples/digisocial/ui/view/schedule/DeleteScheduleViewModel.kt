package com.examples.digisocial.ui.view.schedule

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.repository.ScheduleRepository

data class DeleteScheduleState(
    val isLoading: Boolean = false
)

class DeleteScheduleViewModel : ViewModel() {
    var state = mutableStateOf(DeleteScheduleState())
        private set

    fun deleteSchedule(id: String, onSuccess: () -> Unit){
        ScheduleRepository.deleteSchedule(id = id, onSuccess = onSuccess, onFailure = { } )
    }
}