package com.examples.digisocial.presentation.schedule_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examples.digisocial.domain.models.Response.Loading
import com.examples.digisocial.domain.models.Schedule
import com.examples.digisocial.domain.models.Voluntary
import com.examples.digisocial.domain.repository.AddScheduleResponse
import com.examples.digisocial.domain.repository.DeleteScheduleResponse
import com.examples.digisocial.domain.repository.ScheduleListResponse
import com.examples.digisocial.domain.repository.ScheduleRepository
import com.examples.digisocial.domain.repository.ScheduleResponse
import com.examples.digisocial.domain.repository.UpdateScheduleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleListViewModel @Inject constructor(private val repo: ScheduleRepository) : ViewModel() {
    var scheduleListResponse by mutableStateOf<ScheduleListResponse>(Loading)
        private set
    var scheduleVoluntaryResponse by mutableStateOf<ScheduleResponse>(Loading)
        private set
    var addScheduleResponse by mutableStateOf<AddScheduleResponse>(Loading)
        private set
    var updateScheduleResponse by mutableStateOf<UpdateScheduleResponse>(Loading)
        private set
    var deleteScheduleResponse by mutableStateOf<DeleteScheduleResponse>(Loading)
        private set

    init {
        getScheduleList()
    }

    fun getVoluntaryById(voluntaryId: String) = viewModelScope.launch {
        scheduleVoluntaryResponse = repo.getVoluntaryById(voluntaryId)
    }

    private fun getScheduleList() = viewModelScope.launch {
        repo.getSchedule().collect { response ->
            scheduleListResponse = response
        }
    }

    fun addSchedule(schedule: Schedule) = viewModelScope.launch {
        addScheduleResponse = repo.addSchedule(schedule)
    }

    fun updateSchedule(schedule: Schedule) = viewModelScope.launch {
        updateScheduleResponse = repo.updateSchedule(schedule)
    }

    fun deleteSchedule(id: String) = viewModelScope.launch {
        deleteScheduleResponse = repo.deleteSchedule(id)
    }

    fun addVoluntaryToSchedule(scheduleId: String, voluntary: Voluntary) = viewModelScope.launch {
        repo.addVoluntaryToSchedule(scheduleId, voluntary)
    }
}