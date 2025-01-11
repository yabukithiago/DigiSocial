package com.examples.digisocial.ui.view.schedule

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.repository.ScheduleRepository
import java.util.Date

data class ScheduleState(
    var data: Date = Date(),
    var vagasTotais: Int = 0,
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class CreateScheduleViewModel : ViewModel() {
    var state = mutableStateOf(ScheduleState())
        private set

    private val data
        get() = state.value.data
    private val vagasTotais
        get() = state.value.vagasTotais

    fun onDateChange(newValue: Date) {
        state.value = state.value.copy(data = newValue)
    }

    fun onVagasTotaisChange(newValue: Int) {
        state.value = state.value.copy(vagasTotais = newValue)
    }

    fun addSchedule(onSuccess: () -> Unit) {
        ScheduleRepository.addSchedule(
            data = data,
            vagasTotais = vagasTotais,
            onSuccess = onSuccess,
            onFailure = { })
    }
}