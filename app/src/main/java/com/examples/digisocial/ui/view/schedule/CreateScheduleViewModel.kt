package com.examples.digisocial.ui.view.schedule

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.examples.digisocial.data.repository.ScheduleRepository
import java.util.Date

data class ScheduleState(
    var data: Date = Date(),
    var tarefa: String = "",
    val isLoading: Boolean = false,
    var errorMessage: String? = null
)

class CreateScheduleViewModel : ViewModel() {
    var state = mutableStateOf(ScheduleState())
        private set

    private val data
        get() = state.value.data
    private val tarefa
        get() = state.value.tarefa

    fun onDateChange(newValue: Date) {
        state.value = state.value.copy(data = newValue)
    }

    fun onTarefaChange(newValue: String) {
        state.value = state.value.copy(tarefa = newValue)
    }

    fun addSchedule(id: String, onSuccess: () -> Unit) {
        ScheduleRepository.addSchedule(
            id = id,
            data = data,
            tarefa = tarefa,
            onSuccess = onSuccess,
            onFailure = { })
    }
}