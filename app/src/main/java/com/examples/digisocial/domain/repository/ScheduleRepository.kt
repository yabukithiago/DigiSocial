package com.examples.digisocial.domain.repository

import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.Schedule
import com.examples.digisocial.domain.models.Voluntary
import kotlinx.coroutines.flow.Flow

typealias ScheduleResponse = Response<Schedule>
typealias ScheduleListResponse = Response<List<Schedule>>
typealias AddScheduleResponse = Response<String>
typealias UpdateScheduleResponse = Response<Void>
typealias DeleteScheduleResponse = Response<Void>
typealias AddVoluntaryScheduleResponse = Response<String>

interface ScheduleRepository {

    fun getSchedule(): Flow<ScheduleListResponse>

    suspend fun getScheduleById(id: String): ScheduleResponse

    suspend fun getVoluntaryById(voluntaryId: String): ScheduleResponse

    suspend fun addSchedule(schedule: Schedule): AddScheduleResponse

    suspend fun updateSchedule(schedule: Schedule): UpdateScheduleResponse

    suspend fun deleteSchedule(id: String): DeleteScheduleResponse

    suspend fun addVoluntaryToSchedule(scheduleId: String, voluntary: Voluntary): AddVoluntaryScheduleResponse
}