package com.examples.digisocial.ui.view.schedulevoluntary

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.repository.ScheduleRepository

data class DeleteVoluntaryScheduleState(
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class DeleteVoluntaryScheduleViewModel : ViewModel() {
    var state = mutableStateOf(DeleteVoluntaryScheduleState())
        private set

    fun removeVoluntaryFromSchedule(id: String, voluntaryId: String, onSuccess: () -> Unit,
                                    onFailure: (String) -> Unit) {
        ScheduleRepository.removeVoluntaryFromSchedule(scheduleId = id, voluntaryId = voluntaryId,
            onSuccess = onSuccess, onFailure = onFailure)
    }
}
