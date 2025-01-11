package com.examples.digisocial.ui.view.schedulevoluntary

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.repository.ScheduleRepository

data class VoluntaryScheduleState(
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class RegisterVoluntaryViewModel : ViewModel() {
    var state = mutableStateOf(VoluntaryScheduleState())
        private set

    fun voluntaryScheduleRegister(id: String, voluntaryId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        ScheduleRepository.addVoluntaryToSchedule(scheduleId = id, voluntaryId = voluntaryId, onSuccess = onSuccess, onFailure = onFailure )
    }
}