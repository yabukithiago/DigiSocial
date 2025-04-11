package com.examples.digisocial.domain.repository

import com.examples.digisocial.domain.models.JuntaMember
import com.examples.digisocial.domain.models.Response
import kotlinx.coroutines.flow.Flow

typealias JuntaMemberResponse = Response<JuntaMember>
typealias JuntaMemberListResponse = Response<List<JuntaMember>>
typealias DeleteJuntaMemberResponse = Response<Void>

interface JuntaMemberRepository {
    fun getJuntaMember(): Flow<JuntaMemberListResponse>

    suspend fun getJuntaMemberById(id: String): JuntaMemberResponse

    suspend fun deleteJuntaMember(id: String): DeleteJuntaMemberResponse
}