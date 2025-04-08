package com.examples.digisocial.domain.repository

import com.examples.digisocial.domain.models.Response
import com.examples.digisocial.domain.models.Voluntary
import kotlinx.coroutines.flow.Flow

typealias VoluntaryResponse = Response<Voluntary>
typealias VoluntaryListResponse = Response<List<Voluntary>>
typealias DeleteVoluntaryResponse = Response<Void>

interface VoluntaryRepository {

    fun getVoluntary(): Flow<VoluntaryListResponse>

    suspend fun getVoluntaryById(id: String): VoluntaryResponse

    suspend fun deleteVoluntary(id: String): DeleteVoluntaryResponse
}